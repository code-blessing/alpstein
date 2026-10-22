--
-- Manual amendments to the sakila schema of R__03
--
-- The sakila dump identifies the link tables film_actor and film_category by a composite primary
-- key. This project supports single-column primary keys only, so both tables get a surrogate key
-- of their own here, and the former primary key columns stay behind as plain foreign key columns
-- guarded by a unique constraint.
--
-- The dump also keeps a tsvector search column film.fulltext, maintained by a trigger. This project
-- has no tsvector type, so the column and its trigger are dropped here.
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- Name: film_actor_film_actor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE film_actor_film_actor_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.film_actor_film_actor_id_seq OWNER TO postgres;

--
-- Name: film_actor.film_actor_id; Type: COLUMN; Schema: public; Owner: postgres
--

ALTER TABLE film_actor
    ADD COLUMN film_actor_id integer DEFAULT nextval('film_actor_film_actor_id_seq'::regclass) NOT NULL;

-- Unlike the sequences of R03, which R02 drops one by one, this one is attached to its column, so
-- that the DROP TABLE ... CASCADE of R02 takes it along and R02 needs no amendment of its own.
ALTER SEQUENCE film_actor_film_actor_id_seq OWNED BY film_actor.film_actor_id;

--
-- Name: film_actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY film_actor
    DROP CONSTRAINT film_actor_pkey;

ALTER TABLE ONLY film_actor
    ADD CONSTRAINT film_actor_pkey PRIMARY KEY (film_actor_id);

--
-- Name: film_actor_actor_id_film_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY film_actor
    ADD CONSTRAINT film_actor_actor_id_film_id_key UNIQUE (actor_id, film_id);

--
-- Name: film_category_film_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE film_category_film_category_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.film_category_film_category_id_seq OWNER TO postgres;

--
-- Name: film_category.film_category_id; Type: COLUMN; Schema: public; Owner: postgres
--

ALTER TABLE film_category
    ADD COLUMN film_category_id integer DEFAULT nextval('film_category_film_category_id_seq'::regclass) NOT NULL;

ALTER SEQUENCE film_category_film_category_id_seq OWNED BY film_category.film_category_id;

--
-- Name: film_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY film_category
    DROP CONSTRAINT film_category_pkey;

ALTER TABLE ONLY film_category
    ADD CONSTRAINT film_category_pkey PRIMARY KEY (film_category_id);

--
-- Name: film_category_film_id_category_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY film_category
    ADD CONSTRAINT film_category_film_id_category_id_key UNIQUE (film_id, category_id);

--
-- Name: film_fulltext_trigger; Type: TRIGGER; Schema: public; Owner: postgres
--

DROP TRIGGER film_fulltext_trigger ON film;

--
-- Name: film.fulltext; Type: COLUMN; Schema: public; Owner: postgres
--

-- Takes the gist index film_fulltext_idx of R03 along with it. The column is NOT NULL and only the
-- trigger above ever filled it, so it has to go together with the trigger for R10 to insert films.
ALTER TABLE film
    DROP COLUMN fulltext;
