--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-11-25 21:21:39

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

--
-- TOC entry 11 (class 2615 OID 17142)
-- Name: library; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA library;


ALTER SCHEMA library OWNER TO postgres;

--
-- TOC entry 299 (class 1255 OID 17522)
-- Name: check_duplicate_reader(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.check_duplicate_reader() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
IF EXISTS (
SELECT 1 FROM readers
WHERE fio_reader = NEW.fio_reader
OR phone_reader = NEW.phone_reader
OR email_reader = NEW.email_reader
OR adress_reader = NEW.adress_reader
OR id_card = NEW.id_card
) THEN
RAISE EXCEPTION 'Читатель с такими данными уже существует';
END IF;
RETURN NEW;
END;
$$;


ALTER FUNCTION library.check_duplicate_reader() OWNER TO postgres;

--
-- TOC entry 300 (class 1255 OID 17523)
-- Name: check_duplicate_user(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.check_duplicate_user() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
IF EXISTS (
SELECT 1 FROM users
WHERE fio_user = NEW.fio_user
OR phone_user = NEW.phone_user
OR email_user = NEW.email_user
OR login = new.login
) THEN
RAISE EXCEPTION 'Пользователь с такими данными уже существует';
END IF;
RETURN NEW;
END;
$$;


ALTER FUNCTION library.check_duplicate_user() OWNER TO postgres;

--
-- TOC entry 301 (class 1255 OID 17524)
-- Name: check_isbn(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.check_isbn() RETURNS trigger
    LANGUAGE plpgsql
    AS $_$
BEGIN
IF NEW.isbn !~ '^[0-9]{13}$' THEN
RAISE EXCEPTION 'ISBN must be exactly 13 digits long and contain only numbers.';
END IF;
RETURN NEW;
END;
$_$;


ALTER FUNCTION library.check_isbn() OWNER TO postgres;

--
-- TOC entry 302 (class 1255 OID 17525)
-- Name: check_phone(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.check_phone() RETURNS trigger
    LANGUAGE plpgsql
    AS $_$
BEGIN
IF NEW.phone_user !~ '^[0-9]{11}$' THEN
RAISE EXCEPTION 'Phone number must be exactly 11 digits long and contain only numbers.';
END IF;
RETURN NEW;
END;
$_$;


ALTER FUNCTION library.check_phone() OWNER TO postgres;

--
-- TOC entry 303 (class 1255 OID 17526)
-- Name: check_phone_reader(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.check_phone_reader() RETURNS trigger
    LANGUAGE plpgsql
    AS $_$
BEGIN
IF NEW.phone_reader !~ '^[0-9]{11}$' THEN
RAISE EXCEPTION 'Phone number must be exactly 11 digits long and contain only numbers.';
END IF;
RETURN NEW;
END;
$_$;


ALTER FUNCTION library.check_phone_reader() OWNER TO postgres;

--
-- TOC entry 304 (class 1255 OID 17527)
-- Name: count_books(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.count_books() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
book_count INTEGER;
BEGIN
SELECT SUM(quantity) INTO book_count FROM "library".books;
RETURN book_count;
END; $$;


ALTER FUNCTION library.count_books() OWNER TO postgres;

--
-- TOC entry 306 (class 1255 OID 17540)
-- Name: count_expired_books(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.count_expired_books() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
expired_books_count INTEGER;
BEGIN
SELECT COUNT(*)
 INTO expired_books_count
 FROM "library".books_on_hands
 WHERE deadline < CURRENT_DATE;

 RETURN expired_books_count;
END; $$;


ALTER FUNCTION library.count_expired_books() OWNER TO postgres;

--
-- TOC entry 305 (class 1255 OID 17528)
-- Name: count_issued_books(); Type: FUNCTION; Schema: library; Owner: postgres
--

CREATE FUNCTION library.count_issued_books() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
issued_book_count INTEGER;
BEGIN
SELECT COUNT(*) INTO issued_book_count FROM "library".books_on_hands;
RETURN issued_book_count;
END; $$;


ALTER FUNCTION library.count_issued_books() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 291 (class 1259 OID 17436)
-- Name: ages; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.ages (
    id_age integer NOT NULL,
    name_age character varying(50) NOT NULL
);


ALTER TABLE library.ages OWNER TO postgres;

--
-- TOC entry 290 (class 1259 OID 17435)
-- Name: ages_id_age_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.ages_id_age_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.ages_id_age_seq OWNER TO postgres;

--
-- TOC entry 3552 (class 0 OID 0)
-- Dependencies: 290
-- Name: ages_id_age_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.ages_id_age_seq OWNED BY library.ages.id_age;


--
-- TOC entry 287 (class 1259 OID 17418)
-- Name: autors; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.autors (
    id_autor integer NOT NULL,
    fio_autor character varying(100) NOT NULL,
    description_autor text
);


ALTER TABLE library.autors OWNER TO postgres;

--
-- TOC entry 286 (class 1259 OID 17417)
-- Name: autors_id_autor_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.autors_id_autor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.autors_id_autor_seq OWNER TO postgres;

--
-- TOC entry 3553 (class 0 OID 0)
-- Dependencies: 286
-- Name: autors_id_autor_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.autors_id_autor_seq OWNED BY library.autors.id_autor;


--
-- TOC entry 293 (class 1259 OID 17443)
-- Name: books; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.books (
    id_book integer NOT NULL,
    isbn character varying(50) NOT NULL,
    name_book character varying(100) NOT NULL,
    description_book text,
    id_autor integer,
    id_publishing integer,
    id_age integer,
    photo_book character varying,
    id_genre integer,
    quantity integer
);


ALTER TABLE library.books OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 17442)
-- Name: books_id_book_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.books_id_book_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.books_id_book_seq OWNER TO postgres;

--
-- TOC entry 3554 (class 0 OID 0)
-- Dependencies: 292
-- Name: books_id_book_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.books_id_book_seq OWNED BY library.books.id_book;


--
-- TOC entry 298 (class 1259 OID 17507)
-- Name: books_on_hands; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.books_on_hands (
    id_card integer NOT NULL,
    id_book integer NOT NULL,
    deadline date
);


ALTER TABLE library.books_on_hands OWNER TO postgres;

--
-- TOC entry 297 (class 1259 OID 17493)
-- Name: genres; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.genres (
    id_genre integer NOT NULL,
    name_genre character varying(50) NOT NULL
);


ALTER TABLE library.genres OWNER TO postgres;

--
-- TOC entry 296 (class 1259 OID 17492)
-- Name: genres_id_genre_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.genres_id_genre_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.genres_id_genre_seq OWNER TO postgres;

--
-- TOC entry 3555 (class 0 OID 0)
-- Dependencies: 296
-- Name: genres_id_genre_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.genres_id_genre_seq OWNED BY library.genres.id_genre;


--
-- TOC entry 289 (class 1259 OID 17427)
-- Name: publishings; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.publishings (
    id_publishing integer NOT NULL,
    name_publishing character varying(100) NOT NULL,
    description_publishing text
);


ALTER TABLE library.publishings OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 17426)
-- Name: publishings_id_publishing_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.publishings_id_publishing_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.publishings_id_publishing_seq OWNER TO postgres;

--
-- TOC entry 3556 (class 0 OID 0)
-- Dependencies: 288
-- Name: publishings_id_publishing_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.publishings_id_publishing_seq OWNED BY library.publishings.id_publishing;


--
-- TOC entry 295 (class 1259 OID 17467)
-- Name: readers; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.readers (
    id_reader integer NOT NULL,
    fio_reader character varying(100) NOT NULL,
    phone_reader character varying(20) NOT NULL,
    email_reader character varying(100) NOT NULL,
    adress_reader text,
    id_card integer NOT NULL
);


ALTER TABLE library.readers OWNER TO postgres;

--
-- TOC entry 294 (class 1259 OID 17466)
-- Name: readers_id_reader_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.readers_id_reader_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.readers_id_reader_seq OWNER TO postgres;

--
-- TOC entry 3557 (class 0 OID 0)
-- Dependencies: 294
-- Name: readers_id_reader_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.readers_id_reader_seq OWNED BY library.readers.id_reader;


--
-- TOC entry 283 (class 1259 OID 17399)
-- Name: roles; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.roles (
    id_role integer NOT NULL,
    name_role character varying(50) NOT NULL
);


ALTER TABLE library.roles OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 17398)
-- Name: roles_id_role_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.roles_id_role_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.roles_id_role_seq OWNER TO postgres;

--
-- TOC entry 3558 (class 0 OID 0)
-- Dependencies: 282
-- Name: roles_id_role_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.roles_id_role_seq OWNED BY library.roles.id_role;


--
-- TOC entry 285 (class 1259 OID 17406)
-- Name: users; Type: TABLE; Schema: library; Owner: postgres
--

CREATE TABLE library.users (
    id_user integer NOT NULL,
    fio_user character varying(100) NOT NULL,
    phone_user character varying(20) NOT NULL,
    email_user character varying(100) NOT NULL,
    id_role integer,
    login character varying,
    password character varying
);


ALTER TABLE library.users OWNER TO postgres;

--
-- TOC entry 284 (class 1259 OID 17405)
-- Name: users_id_user_seq; Type: SEQUENCE; Schema: library; Owner: postgres
--

CREATE SEQUENCE library.users_id_user_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE library.users_id_user_seq OWNER TO postgres;

--
-- TOC entry 3559 (class 0 OID 0)
-- Dependencies: 284
-- Name: users_id_user_seq; Type: SEQUENCE OWNED BY; Schema: library; Owner: postgres
--

ALTER SEQUENCE library.users_id_user_seq OWNED BY library.users.id_user;


--
-- TOC entry 3352 (class 2604 OID 17439)
-- Name: ages id_age; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.ages ALTER COLUMN id_age SET DEFAULT nextval('library.ages_id_age_seq'::regclass);


--
-- TOC entry 3350 (class 2604 OID 17421)
-- Name: autors id_autor; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.autors ALTER COLUMN id_autor SET DEFAULT nextval('library.autors_id_autor_seq'::regclass);


--
-- TOC entry 3353 (class 2604 OID 17446)
-- Name: books id_book; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books ALTER COLUMN id_book SET DEFAULT nextval('library.books_id_book_seq'::regclass);


--
-- TOC entry 3355 (class 2604 OID 17496)
-- Name: genres id_genre; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.genres ALTER COLUMN id_genre SET DEFAULT nextval('library.genres_id_genre_seq'::regclass);


--
-- TOC entry 3351 (class 2604 OID 17430)
-- Name: publishings id_publishing; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.publishings ALTER COLUMN id_publishing SET DEFAULT nextval('library.publishings_id_publishing_seq'::regclass);


--
-- TOC entry 3354 (class 2604 OID 17470)
-- Name: readers id_reader; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.readers ALTER COLUMN id_reader SET DEFAULT nextval('library.readers_id_reader_seq'::regclass);


--
-- TOC entry 3348 (class 2604 OID 17402)
-- Name: roles id_role; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.roles ALTER COLUMN id_role SET DEFAULT nextval('library.roles_id_role_seq'::regclass);


--
-- TOC entry 3349 (class 2604 OID 17409)
-- Name: users id_user; Type: DEFAULT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.users ALTER COLUMN id_user SET DEFAULT nextval('library.users_id_user_seq'::regclass);


--
-- TOC entry 3539 (class 0 OID 17436)
-- Dependencies: 291
-- Data for Name: ages; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.ages (id_age, name_age) VALUES (1, '0+');
INSERT INTO library.ages (id_age, name_age) VALUES (2, '6+');
INSERT INTO library.ages (id_age, name_age) VALUES (3, '12+');
INSERT INTO library.ages (id_age, name_age) VALUES (4, '16+');
INSERT INTO library.ages (id_age, name_age) VALUES (5, '18+');


--
-- TOC entry 3535 (class 0 OID 17418)
-- Dependencies: 287
-- Data for Name: autors; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.autors (id_autor, fio_autor, description_autor) VALUES (1, 'Антуан де Сент-Экзюпери', 'Антуан де Сент-Экзюпери (29 июня 1900 — 31 июля 1944) — французский писатель, журналист, поэт, сценарист и профессиональный лётчик.');
INSERT INTO library.autors (id_autor, fio_autor, description_autor) VALUES (2, 'Марк Твен', 'Марк Твен (настоящее имя Сэмюэл Ленгхорн Клеменс) — американский писатель, юморист, журналист и общественный деятель. Его творчество охватывает множество жанров — юмор, сатиру, философскую фантастику, публицистику и другие, и во всех этих жанрах он неизменно занимает позицию гуманиста и демократа.');
INSERT INTO library.autors (id_autor, fio_autor, description_autor) VALUES (4, 'Фёдор Достоевский', 'Фёдор Михайлович Достоевский (30 октября [11 ноября] 1821 — 28 января [9 февраля] 1881) — русский писатель, мыслитель, философ и публицист.');
INSERT INTO library.autors (id_autor, fio_autor, description_autor) VALUES (5, 'Лев Толстой', 'Лев Николаевич Толстой (28 августа [9 сентября] 1828 — 7 [20] ноября 1910) — один из наиболее известных русских писателей и мыслителей, один из величайших в мире писателей-романистов.');
INSERT INTO library.autors (id_autor, fio_autor, description_autor) VALUES (3, 'Оскар Уайльд', 'Оскар Уайльд (1854–1900) — ирландский писатель и поэт. Один из самых известных драматургов позднего Викторианского периода, одна из ключевых фигур эстетизма и европейского модернизма. ');


--
-- TOC entry 3541 (class 0 OID 17443)
-- Dependencies: 293
-- Data for Name: books; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.books (id_book, isbn, name_book, description_book, id_autor, id_publishing, id_age, photo_book, id_genre, quantity) VALUES (3, '9785041757076', 'Маленький принц', '«Маленький принц» — самая известная сказка французского писателя Антуана де Сент-Экзюпери. Она стала одной из самых переводных книг в истории и вошла в список мировой золотой литературы.', 1, 1, 2, 'src\main\resources\photos\DSC_7116.png', 3, 8);
INSERT INTO library.books (id_book, isbn, name_book, description_book, id_autor, id_publishing, id_age, photo_book, id_genre, quantity) VALUES (5, '9785041567736', 'Портрет Дориана Грея', ' интригующая история человека, пожелавшего навеки сохранить молодость и заставить собственный портрет стареть вместо себя. Дориан Грей, аристократ удивительной красоты, продал душу в обмен на исполнение желания. Но он не знал, что цена окажется слишком высока. Он перестанет различать добро и зло и будет разбивать сердца, толкать людей на самоубийство, предавать. Искусство окажется губительнее, чем думал Дориан.', 3, 2, 4, 'src\main\resources\photos\ND00033171-800x1000.jpg', 4, 0);
INSERT INTO library.books (id_book, isbn, name_book, description_book, id_autor, id_publishing, id_age, photo_book, id_genre, quantity) VALUES (2, '9785171131449', 'Принц и нищий', 'Роман Марка Твена (1835–1910) "Принц и нищий" — это первое историческое произведение писателя, которое было опубликовано в 1881 году. Сюжет романа разворачивается в Англии в середине ХVI века, когда в один и тот же день на свет появились два мальчика, похожие друг на друга, но только один родился принцем, а другой — нищим. Безобидная шутка этих мальчишек, которые случайно встретились, обернулась настоящей трагедией для всей Англии. Роман о поменявшихся местами наследнике английского престола и мальчике-попрошайке со Двора Отбросов по праву относят к классике мировой литературы.', 2, 3, 2, 'src\main\resources\photos\200531777-256-k534982.jpg', 4, 5);
INSERT INTO library.books (id_book, isbn, name_book, description_book, id_autor, id_publishing, id_age, photo_book, id_genre, quantity) VALUES (4, '9785171535650', 'Анна Каренина', '"Анну Каренину" по праву называют самым известным и популярным в мире романом о любви.', 5, 4, 3, 'src\main\resources\photos\304652.jpg', 4, 7);
INSERT INTO library.books (id_book, isbn, name_book, description_book, id_autor, id_publishing, id_age, photo_book, id_genre, quantity) VALUES (6, '9785041166779', 'Идиот', 'Такие насыщенные сюжеты всегда «обречены» вызывать в читателе автоматический эффект присутствия. Секрет не только и не столько в событийных перипетиях, сколько в великолепно выписанных персонажах и электрических разрядах между ними. Суммой всех слагаемых становится превращение эффекта присутствия в «эффект соучастия»: погружаясь в роман, вы становитесь сначала невидимым свидетелем событий, а потом сживаетесь с главным героем так прочно, что смотрите его глазами и меряете его мир биением собственного сердца. И тогда возникает главный вопрос: как выжить в этом мире, который казался вымышленным и вдруг стал таким реальным, и как здесь не сойти с ума. В конце концов, может ли общество определять степень чьей-то «нормальности»? Вообще, кто из нас князь Мышкин?', 4, 3, 4, 'src\main\resources\photos\1oQs_oSIDo0.jpg', 4, 7);


--
-- TOC entry 3546 (class 0 OID 17507)
-- Dependencies: 298
-- Data for Name: books_on_hands; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.books_on_hands (id_card, id_book, deadline) VALUES (2, 2, '2023-11-25');
INSERT INTO library.books_on_hands (id_card, id_book, deadline) VALUES (1, 5, '2023-11-25');
INSERT INTO library.books_on_hands (id_card, id_book, deadline) VALUES (3, 6, '2023-12-09');
INSERT INTO library.books_on_hands (id_card, id_book, deadline) VALUES (3, 5, '2023-12-09');
INSERT INTO library.books_on_hands (id_card, id_book, deadline) VALUES (2, 5, '2023-12-09');


--
-- TOC entry 3545 (class 0 OID 17493)
-- Dependencies: 297
-- Data for Name: genres; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.genres (id_genre, name_genre) VALUES (1, 'Драма');
INSERT INTO library.genres (id_genre, name_genre) VALUES (2, 'Триллер');
INSERT INTO library.genres (id_genre, name_genre) VALUES (3, 'Сказка');
INSERT INTO library.genres (id_genre, name_genre) VALUES (4, 'Роман');
INSERT INTO library.genres (id_genre, name_genre) VALUES (5, 'Повесть');


--
-- TOC entry 3537 (class 0 OID 17427)
-- Dependencies: 289
-- Data for Name: publishings; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.publishings (id_publishing, name_publishing, description_publishing) VALUES (1, 'Издательская группа АСТ', 'Издательская группа АСТ — одно из крупнейших издательств, занимающее лидирующие позиции на российском книжном рынке, основанное в 1990 году. АСТ издает книги практически всех жанров для самой широкой аудитории. Это интеллектуальная и развлекательная литература, русская и зарубежная классика, учебники и учебные пособия, прикладные книги. Издательство выпускает более 40 млн. экземпляров книг в год и объединяет сильнейшую в стране редакционную команду.');
INSERT INTO library.publishings (id_publishing, name_publishing, description_publishing) VALUES (3, 'Феникс', 'Издательство "Феникс" (Ростов) выпускает широкий спектр учебной и популярной литературы по медицине и ветеринарии, юриспруденции и экономике, социальным и естественным наукам, прикладной и технической литературы, литературы по спорту и боевым искусствам; детской и педагогической литературы; литературы по кулинарии и рукоделию.');
INSERT INTO library.publishings (id_publishing, name_publishing, description_publishing) VALUES (4, 'Росмэн', '«Росмэн» — российское детское издательство. Уже 20 лет издательство отбирает и издает самые лучшие книги отечественных и зарубежных авторов — книги, которые становятся лучшими друзьями с первых месяцев жизни и с которыми наши дети развиваются и растут, познавая красоту и многообразие окружающего мира.');
INSERT INTO library.publishings (id_publishing, name_publishing, description_publishing) VALUES (2, 'Издательство «Эксмо»', 'Издательство «Эксмо» — одна из двух крупнейших книгоиздательских компаний России. Издательство было основано в 1991 г. Быстро вышло в лидеры рынка, благодаря сотрудничеству с популярными авторами, такими как Александра Маринина, Илья Деревянко, Дарья Донцова. Среди известных серий издательства — «Иронический детектив», «Чёрный котёнок», и др.');


--
-- TOC entry 3543 (class 0 OID 17467)
-- Dependencies: 295
-- Data for Name: readers; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.readers (id_reader, fio_reader, phone_reader, email_reader, adress_reader, id_card) VALUES (2, 'Соколов Валентин', '89306662738', 'sokolov@gmail.com', 'Вторчермета 14', 1);
INSERT INTO library.readers (id_reader, fio_reader, phone_reader, email_reader, adress_reader, id_card) VALUES (1, 'Спиридонова Мария', '89206663849', 'spiridonova@gmail.com', 'Мещерский бульвар 5', 3);
INSERT INTO library.readers (id_reader, fio_reader, phone_reader, email_reader, adress_reader, id_card) VALUES (3, 'Сокуров Александр', '89406663847', 'sokurov@gmail.com', 'Студенческая 6б', 2);


--
-- TOC entry 3531 (class 0 OID 17399)
-- Dependencies: 283
-- Data for Name: roles; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.roles (id_role, name_role) VALUES (1, 'Сотрудник');
INSERT INTO library.roles (id_role, name_role) VALUES (2, 'Управляющий библиотекой');
INSERT INTO library.roles (id_role, name_role) VALUES (3, 'Директор библиотеки');


--
-- TOC entry 3533 (class 0 OID 17406)
-- Dependencies: 285
-- Data for Name: users; Type: TABLE DATA; Schema: library; Owner: postgres
--

INSERT INTO library.users (id_user, fio_user, phone_user, email_user, id_role, login, password) VALUES (3, 'Виктория Соловьева', '89203566345', 'twix@gmail.com', 1, '321', '123');
INSERT INTO library.users (id_user, fio_user, phone_user, email_user, id_role, login, password) VALUES (2, 'Виталий Шнайдерман', '89306674644', 'vital@gmail.com', 3, '123', '321');
INSERT INTO library.users (id_user, fio_user, phone_user, email_user, id_role, login, password) VALUES (4, 'Орлов Дмитрий', '89526664736', 'orlov@gmail.com', 2, 'orlov', 'orlov123');


--
-- TOC entry 3560 (class 0 OID 0)
-- Dependencies: 290
-- Name: ages_id_age_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.ages_id_age_seq', 5, true);


--
-- TOC entry 3561 (class 0 OID 0)
-- Dependencies: 286
-- Name: autors_id_autor_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.autors_id_autor_seq', 5, true);


--
-- TOC entry 3562 (class 0 OID 0)
-- Dependencies: 292
-- Name: books_id_book_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.books_id_book_seq', 1, true);


--
-- TOC entry 3563 (class 0 OID 0)
-- Dependencies: 296
-- Name: genres_id_genre_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.genres_id_genre_seq', 5, true);


--
-- TOC entry 3564 (class 0 OID 0)
-- Dependencies: 288
-- Name: publishings_id_publishing_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.publishings_id_publishing_seq', 4, true);


--
-- TOC entry 3565 (class 0 OID 0)
-- Dependencies: 294
-- Name: readers_id_reader_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.readers_id_reader_seq', 1, false);


--
-- TOC entry 3566 (class 0 OID 0)
-- Dependencies: 282
-- Name: roles_id_role_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.roles_id_role_seq', 3, true);


--
-- TOC entry 3567 (class 0 OID 0)
-- Dependencies: 284
-- Name: users_id_user_seq; Type: SEQUENCE SET; Schema: library; Owner: postgres
--

SELECT pg_catalog.setval('library.users_id_user_seq', 3, true);


--
-- TOC entry 3365 (class 2606 OID 17441)
-- Name: ages ages_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.ages
    ADD CONSTRAINT ages_pkey PRIMARY KEY (id_age);


--
-- TOC entry 3361 (class 2606 OID 17425)
-- Name: autors autors_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.autors
    ADD CONSTRAINT autors_pkey PRIMARY KEY (id_autor);


--
-- TOC entry 3375 (class 2606 OID 17511)
-- Name: books_on_hands books_on_hands_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books_on_hands
    ADD CONSTRAINT books_on_hands_pkey PRIMARY KEY (id_card, id_book);


--
-- TOC entry 3367 (class 2606 OID 17450)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id_book);


--
-- TOC entry 3373 (class 2606 OID 17498)
-- Name: genres genres_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.genres
    ADD CONSTRAINT genres_pkey PRIMARY KEY (id_genre);


--
-- TOC entry 3363 (class 2606 OID 17434)
-- Name: publishings publishings_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.publishings
    ADD CONSTRAINT publishings_pkey PRIMARY KEY (id_publishing);


--
-- TOC entry 3369 (class 2606 OID 17476)
-- Name: readers readers_id_card_key; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.readers
    ADD CONSTRAINT readers_id_card_key UNIQUE (id_card);


--
-- TOC entry 3371 (class 2606 OID 17474)
-- Name: readers readers_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.readers
    ADD CONSTRAINT readers_pkey PRIMARY KEY (id_reader);


--
-- TOC entry 3357 (class 2606 OID 17404)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id_role);


--
-- TOC entry 3359 (class 2606 OID 17411)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id_user);


--
-- TOC entry 3386 (class 2620 OID 17531)
-- Name: readers check_duplicate_reader_trigger; Type: TRIGGER; Schema: library; Owner: postgres
--

CREATE TRIGGER check_duplicate_reader_trigger BEFORE INSERT ON library.readers FOR EACH ROW EXECUTE FUNCTION library.check_duplicate_reader();


--
-- TOC entry 3383 (class 2620 OID 17533)
-- Name: users check_duplicate_user_trigger; Type: TRIGGER; Schema: library; Owner: postgres
--

CREATE TRIGGER check_duplicate_user_trigger BEFORE INSERT ON library.users FOR EACH ROW EXECUTE FUNCTION library.check_duplicate_user();


--
-- TOC entry 3385 (class 2620 OID 17529)
-- Name: books isbn_check; Type: TRIGGER; Schema: library; Owner: postgres
--

CREATE TRIGGER isbn_check BEFORE INSERT OR UPDATE OF isbn ON library.books FOR EACH ROW EXECUTE FUNCTION library.check_isbn();


--
-- TOC entry 3384 (class 2620 OID 17532)
-- Name: users phone_check; Type: TRIGGER; Schema: library; Owner: postgres
--

CREATE TRIGGER phone_check BEFORE INSERT OR UPDATE OF phone_user ON library.users FOR EACH ROW EXECUTE FUNCTION library.check_phone();


--
-- TOC entry 3387 (class 2620 OID 17530)
-- Name: readers phone_reader_check; Type: TRIGGER; Schema: library; Owner: postgres
--

CREATE TRIGGER phone_reader_check BEFORE INSERT OR UPDATE OF phone_reader ON library.readers FOR EACH ROW EXECUTE FUNCTION library.check_phone_reader();


--
-- TOC entry 3377 (class 2606 OID 17461)
-- Name: books books_id_age_fkey; Type: FK CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books
    ADD CONSTRAINT books_id_age_fkey FOREIGN KEY (id_age) REFERENCES library.ages(id_age);


--
-- TOC entry 3378 (class 2606 OID 17451)
-- Name: books books_id_autor_fkey; Type: FK CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books
    ADD CONSTRAINT books_id_autor_fkey FOREIGN KEY (id_autor) REFERENCES library.autors(id_autor);


--
-- TOC entry 3379 (class 2606 OID 17456)
-- Name: books books_id_publishing_fkey; Type: FK CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books
    ADD CONSTRAINT books_id_publishing_fkey FOREIGN KEY (id_publishing) REFERENCES library.publishings(id_publishing);


--
-- TOC entry 3381 (class 2606 OID 17517)
-- Name: books_on_hands books_on_hands_id_book_fkey; Type: FK CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books_on_hands
    ADD CONSTRAINT books_on_hands_id_book_fkey FOREIGN KEY (id_book) REFERENCES library.books(id_book);


--
-- TOC entry 3382 (class 2606 OID 17512)
-- Name: books_on_hands books_on_hands_id_card_fkey; Type: FK CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books_on_hands
    ADD CONSTRAINT books_on_hands_id_card_fkey FOREIGN KEY (id_card) REFERENCES library.readers(id_card);


--
-- TOC entry 3380 (class 2606 OID 17499)
-- Name: books fk_books_genres; Type: FK CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.books
    ADD CONSTRAINT fk_books_genres FOREIGN KEY (id_genre) REFERENCES library.genres(id_genre);


--
-- TOC entry 3376 (class 2606 OID 17412)
-- Name: users users_id_role_fkey; Type: FK CONSTRAINT; Schema: library; Owner: postgres
--

ALTER TABLE ONLY library.users
    ADD CONSTRAINT users_id_role_fkey FOREIGN KEY (id_role) REFERENCES library.roles(id_role);


-- Completed on 2023-11-25 21:21:39

--
-- PostgreSQL database dump complete
--

