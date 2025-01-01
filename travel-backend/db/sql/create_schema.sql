CREATE TABLE app_user
(
    dni             varchar(20) NOT NULL,
    full_name       varchar(50) NOT NULL,
    phone_number    varchar(20) NULL,
    total_flights   int NOT NULL,
    total_lodgings  int NOT NULL,
    total_tours     int NOT NULL,
    email           varchar(30) NOT NULL,
    password        varchar(120) NOT NULL,
    profile_picture_path varchar(360) NULL,
    CONSTRAINT pk_app_user PRIMARY KEY ( dni )
);

CREATE TABLE flight
(
    "id"           bigserial NOT NULL,
    origin_lat   decimal NOT NULL,
    origin_lng   decimal NOT NULL,
    destination_lng  decimal NOT NULL,
    destination_lat  decimal NOT NULL,
    origin_name  varchar(20) NOT NULL,
    destination_name varchar(20) NOT NULL,
    aero_line varchar(20) NOT NULL,
    price double precision NOT NULL,
    CONSTRAINT pk_flight PRIMARY KEY ( "id" )
);


CREATE TABLE hotel
(
    "id"      bigserial NOT NULL,
    name    varchar(50) NOT NULL,
    address varchar(50) NOT NULL,
    rating int NOT NULL,
    price    double precision NOT NULL,
    CONSTRAINT pk_hotel PRIMARY KEY ( "id" )
);

CREATE TABLE tour
(
    "id"                bigserial NOT NULL,
    id_customer         varchar(20) NOT NULL,
    name                varchar(20) NOT NULL,
    CONSTRAINT pk_tour PRIMARY KEY ( "id" ),
    CONSTRAINT fk_app_user FOREIGN KEY ( id_customer ) REFERENCES app_user ( dni ) ON DELETE NO ACTION
);

CREATE TABLE reservation
(
    "id"             uuid NOT NULL,
    date_reservation timestamp NOT NULL,
    date_start       date NOT NULL,
    date_end         date NULL,
    total_days       int NOT NULL,
    price            double precision not null,
    tour_id          bigint NULL,
    hotel_id         bigint NOT NULL,
    customer_id      varchar(20) NOT NULL,
    CONSTRAINT pk_reservation PRIMARY KEY ( "id" ),
    CONSTRAINT fk_app_user_r FOREIGN KEY ( customer_id ) REFERENCES app_user ( dni ) ON DELETE NO ACTION ,
    CONSTRAINT fk_hotel_r FOREIGN KEY ( hotel_id ) REFERENCES hotel ( "id" ) ON DELETE NO ACTION ,
    CONSTRAINT fk_tour_r FOREIGN KEY ( tour_id ) REFERENCES tour ( "id" ) ON DELETE CASCADE
);

CREATE TABLE ticket
(
    "id"           uuid NOT NULL,
    price          double precision NOT NULL,
    flight_id      bigint NOT NULL,
    customer_id    varchar(20) NOT NULL,
    departure_date timestamp NOT NULL,
    arrival_date   timestamp NOT NULL,
    purchase_date  timestamp NOT NULL,
    tour_id   bigint,
    CONSTRAINT pk_ticket PRIMARY KEY ( "id" ),
    CONSTRAINT fk_app_user_t FOREIGN KEY ( customer_id ) REFERENCES app_user ( dni ) ON DELETE NO ACTION,
    CONSTRAINT fk_flight_t FOREIGN KEY ( flight_id ) REFERENCES flight ( "id" ) ON DELETE NO ACTION,
    CONSTRAINT fk_tour_t FOREIGN KEY ( tour_id ) REFERENCES tour ( "id" ) ON DELETE CASCADE
);