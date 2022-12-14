--
-- PostgreSQL database dump
--

-- Dumped from database version 11.16
-- Dumped by pg_dump version 11.16

-- Started on 2022-07-31 19:16:21

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 24637)
-- Name: d_cat_catalog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.d_cat_catalog (
    catalog_id integer NOT NULL,
    delivery_date date,
    company character varying(100),
    uuid character varying(100)
);


ALTER TABLE public.d_cat_catalog OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 24642)
-- Name: d_cat_catalog_catalog_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.d_cat_catalog ALTER COLUMN catalog_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.d_cat_catalog_catalog_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 198 (class 1259 OID 24644)
-- Name: f_cat_plants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.f_cat_plants (
    plants_id integer NOT NULL,
    common character varying(100),
    botanical character varying(100),
    zone integer,
    light character varying(100),
    price numeric,
    availability integer,
    catalog_id integer
);


ALTER TABLE public.f_cat_plants OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 24652)
-- Name: f_cat_plants_plants_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.f_cat_plants ALTER COLUMN plants_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.f_cat_plants_plants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 2818 (class 0 OID 24637)
-- Dependencies: 196
-- Data for Name: d_cat_catalog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.d_cat_catalog (catalog_id, delivery_date, company, uuid) FROM stdin;
31	2019-08-17	Grandpa plants	hj5u894hfa89shr89fa9s8
32	2019-02-12	Flowers for people	2hk23kj23k23khkjhhf89q
33	2019-03-23	Garden plants	o1jml3jdgh23hg89asfg983
34	2019-10-12	Flowers for a wedding	h98asd908ghauh321ugf89
\.


--
-- TOC entry 2820 (class 0 OID 24644)
-- Dependencies: 198
-- Data for Name: f_cat_plants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.f_cat_plants (plants_id, common, botanical, zone, light, price, availability, catalog_id) FROM stdin;
30	??????????????????????	Hepatica americana	4	???? ?????????????? ?????????? ????????	3.99	10299	31
31	?????????????? ??????????????????????	Arisaema triphyllum	4	???? ?????????????? ?????????? ????????	3.23	20199	31
32	??????????????	Podophyllum peltatum	3	???? ?????????????? ?????????? ????????	2.98	60599	31
33	??????????, ???????????????? ??????????????????	Phlox divaricata	3	???????? ?????? ????????	2.8	12299	31
34	???????????????? ????????????	Sanguinaria canadensis	4	???? ?????????????? ?????????? ????????	2.44	31599	32
35	??????????????????	Aquilegia canadensis	3	???? ?????????????? ?????????? ????????	9.37	30699	32
36	???????????????? ??????????????????	Caltha palustris	4	???? ?????????????? ?????????? ????????	6.81	51799	32
37	??????????????????	Caltha palustris	4	???? ?????????????? ?????????? ????????	9.9	30699	33
38	?????????? ????????????????????	Dicentra cucullaria	3	???? ?????????????? ?????????? ????????	6.44	12099	33
39	?????????? ????????????	Asarum canadense	3	???? ?????????????? ?????????? ????????	9.03	41899	33
40	??????????????????	Hepatica americana	4	???? ?????????????? ?????????? ????????	4.45	12699	33
41	??????????, ??????????????	Phlox divaricata	3	???????? ?????? ????????	5.59	21699	34
42	??????????????????	Claytonia Virginica	7	???? ?????????????? ?????????? ????????	6.59	20199	34
43	????????????????	Trillium grandiflorum	5	???????? ?????? ????????	3.9	42999	34
44	????????????????	Trillium grandiflorum	5	???????? ?????? ????????	3.2	22199	34
\.


--
-- TOC entry 2827 (class 0 OID 0)
-- Dependencies: 197
-- Name: d_cat_catalog_catalog_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.d_cat_catalog_catalog_id_seq', 34, true);


--
-- TOC entry 2828 (class 0 OID 0)
-- Dependencies: 199
-- Name: f_cat_plants_plants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.f_cat_plants_plants_id_seq', 46, true);


--
-- TOC entry 2693 (class 2606 OID 24641)
-- Name: d_cat_catalog d_cat_catalog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.d_cat_catalog
    ADD CONSTRAINT d_cat_catalog_pkey PRIMARY KEY (catalog_id);


--
-- TOC entry 2695 (class 2606 OID 24651)
-- Name: f_cat_plants f_cat_plants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.f_cat_plants
    ADD CONSTRAINT f_cat_plants_pkey PRIMARY KEY (plants_id);


--
-- TOC entry 2696 (class 2606 OID 24654)
-- Name: f_cat_plants fc_f_cat_plants; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.f_cat_plants
    ADD CONSTRAINT fc_f_cat_plants FOREIGN KEY (catalog_id) REFERENCES public.d_cat_catalog(catalog_id) NOT VALID;


-- Completed on 2022-07-31 19:16:22

--
-- PostgreSQL database dump complete
--

