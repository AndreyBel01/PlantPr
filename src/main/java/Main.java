import model.Catalog;
import model.Plant;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.sql.DriverManager.getConnection;

public class Main {

    private static final String TAG_PLANT = "PLANT";
    private static final String TAG_COMMON = "COMMON";
    private static final String TAG_BOTANICAL = "BOTANICAL";
    private static final String TAG_ZONE = "ZONE";
    private static final String TAG_LIGHT = "LIGHT";
    private static final String TAG_PRICE = "PRICE";
    private static final String TAG_AVAILABILITY = "AVAILABILITY";
    private static final String ATTRIBUTE_UUID = "uuid";
    private static final String ATTRIBUTE_DATE = "date";
    private static final String ATTRIBUTE_COMPANY = "company";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "13243546";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/plant";

    public static void main(String[] args) {

        //Подключенмие к БД;
        Connection con = connectionDB();

        Catalog catalog = new Catalog();

        //Открытие файла
        Document doc;
        Scanner scanner = new Scanner(System.in);

        try {

            doc = buildDocument(scanner.nextLine());

        } catch (Exception e) {

            System.out.println("Open parsing error ");
            return;

        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        //Получаем содержимое элемента Catalog
        Node catalogNode = doc.getFirstChild();
        NodeList catalogChild = catalogNode.getChildNodes();
        //Получаем атрибуты элемента Catalog
        NamedNodeMap attributes = catalogNode.getAttributes();
        //Записываем атрибуты элемента Catalog
        for (int i = 0; i < attributes.getLength(); i++) {

            if (attributes.item(i).getTextContent() == null) continue;

            switch (attributes.item(i).getNodeName()) {
                case ATTRIBUTE_UUID -> catalog.setUuid(attributes.item(i).getTextContent());
                case ATTRIBUTE_DATE -> catalog.setDate(LocalDate.parse(attributes.item(i).getTextContent(), formatter));
                case ATTRIBUTE_COMPANY -> catalog.setCompany(attributes.item(i).getTextContent());
            }

        }

        //Запись атрибутов
        String sql = "insert into d_cat_catalog (delivery_date ,company, uuid) values (?,? ,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(catalog.getDate()));
            preparedStatement.setString(2, catalog.getCompany());
            preparedStatement.setString(3,  catalog.getUuid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error");
            throw new RuntimeException(e);
        }

        int catId;
        //Значение catalog_id
        catId = catalogId(con, catalog);

        List<Plant> plantList = new ArrayList<>();
        //Парсинг catalog
        for (int i = 0; i <  catalogChild.getLength(); i++) {

            if (catalogChild.item(i).getNodeType() != Node.ELEMENT_NODE) continue;
            if (!catalogChild.item(i).getNodeName().equals(TAG_PLANT)) continue;

            //Парсинг Plant
            Plant plant = parsPlant(catalogChild.item(i));

            //Заполнение Plant
            String sqlSetPlant = "insert into f_cat_plants (common, botanical, zone, light, " +
                    " price, availability, catalog_id) " +
                    "values (?, ?, ?, ?, ?, ?, "+ catId +")";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sqlSetPlant);
                assert plant != null;
                preparedStatement.setString(1, plant.getCommon());
                preparedStatement.setString(2, plant.getBotanical());
                preparedStatement.setInt(3,  plant.getZone());
                preparedStatement.setString(4, plant.getLight());
                preparedStatement.setDouble(5, plant.getPrice());
                preparedStatement.setInt(6, plant.getAvailability());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SQL error");
                throw new RuntimeException(e);
            }

            plantList.add(plant);

        }

        catalog.setPlant(plantList);

        System.out.println(catalog.toString());

    }

    //Открытие файла
    private static Document buildDocument(String xmlPath) throws Exception {

        File file = new File(xmlPath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder().parse(file);

    }

    private static Connection connectionDB() {

        try {
            return getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection error");
            throw new RuntimeException(e);
        }

    }

    private static Plant parsPlant(Node plant) {

        String common = "";
        String botanical = "";
        int zone = 0;
        String light = "";
        double price = 0;
        int availability = 0;

        NodeList plantChields = plant.getChildNodes();

        for (int i = 0; i < plantChields.getLength(); i++) {

            if (plantChields.item(i).getNodeType() != Node.ELEMENT_NODE) continue;

            switch (plantChields.item(i).getNodeName()) {
                case TAG_COMMON -> common = plantChields.item(i).getTextContent();
                case TAG_BOTANICAL -> botanical = plantChields.item(i).getTextContent();
                case TAG_ZONE -> zone = Integer.parseInt(plantChields.item(i).getTextContent());
                case TAG_LIGHT -> light = plantChields.item(i).getTextContent();
                case TAG_PRICE -> {
                    try {

                        price = Double.parseDouble((plantChields.item(i).getTextContent()).substring(1));
                    } catch (Exception e) {
                        System.out.println("String error ");
                        return null;
                    }
                }
                case TAG_AVAILABILITY -> availability = Integer.parseInt(plantChields.item(i).getTextContent());
            }

        }
        return new Plant(common, botanical, zone, light, price, availability);
    }

    //Значение catalog_id
    private static int catalogId(Connection con, Catalog catalog) {
        try {
            int calId = 0;
            Statement statement = con.createStatement();
            String sqlSelectUuidTask = "select catalog_id " +
                    "from d_cat_catalog d " +
                    "where(d.uuid = '"+ catalog.getUuid() +"')";
            ResultSet result = statement.executeQuery(sqlSelectUuidTask);
            while (result.next()) calId = result.getInt("catalog_id");
            return  calId;
        } catch (SQLException e) {
            System.out.println("SQL error");
            throw new RuntimeException(e);
        }
    }
}
