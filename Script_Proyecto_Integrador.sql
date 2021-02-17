
SCRIPT PROYECTO INTEGRADOR:

CREATE TABLE LUGAR(Cod_Lugar number(3) primary key,
tipo_lugar varchar2(30) not null,
numero_lugar number(3) not null);

CREATE TABLE REGISTRO(cod_registro number(3) primary key,
cod_lugar number(3) references LUGAR(cod_lugar),
fecha_registro date not null,
hora_llegada number(5) not null,
llamado varchar2(10) not null check (llamado in ('true', 'false')),
turno number(3) not null,
motivo_consulta varchar2(40) not null,
tipo_usuario varchar2(2) not null);

CREATE TABLE TITULAR(cod_titular number(3) primary key,
nombre varchar2(40) not null,
apellidos varchar(80) not null,
correo varchar2(40) not null,
identificacion varchar2(20) not null,
fecha_nac date not null,
numero_cta varchar2(20) not null,
pin varchar2(4) not null);

CREATE TABLE REGISTRO_TITULAR(cod_registro_titular number(3) primary key,
cod_registro number(3) references REGISTRO(cod_registro),
cod_titular number(3) references TITULAR(cod_titular));

CREATE TABLE LOGIN_EMPLEADO(cod_login_empleado number(3) primary key,
username varchar2(40) not null,
password varchar2(40) not null,
cargo varchar2(15) not null);
 
CREATE SEQUENCE SEQ_COD_REGISTRO;
CREATE SEQUENCE SEQ_COD_REGISTRO_TITULAR;
CREATE SEQUENCE SEQ_COD_LUGAR; 
CREATE SEQUENCE SEQ_COD_TITULAR;
CREATE SEQUENCE SEQ_COD_LOGIN_EMPLEADO;

INSERT INTO LUGAR VALUES(0, 'WAITING', 0);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'CAJA', 1);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'CAJA', 2);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'CAJA', 3);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'CAJA', 4);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'CAJA', 5);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'ASESOR', 1);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'ASESOR', 2);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'ASESOR', 3);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'ASESOR', 4);
INSERT INTO LUGAR VALUES(SEQ_COD_LUGAR.Nextval, 'ASESOR', 5);

INSERT INTO TITULAR VALUES(SEQ_COD_TITULAR.NEXTVAL, 'JHOAN', 'J', 'TITULAR@EMAIL.COM', '5445544111', '15/FEB/85', '635571', '1234');


INSERT INTO LOGIN_EMPLEADO VALUES(SEQ_COD_LOGIN_EMPLEADO.NEXTVAL, 'ADMIN', '12345', 'Administrador');
INSERT INTO LOGIN_EMPLEADO VALUES(SEQ_COD_LOGIN_EMPLEADO.NEXTVAL, 'Mrql', '98765', 'Cajero');
INSERT INTO LOGIN_EMPLEADO VALUES(SEQ_COD_LOGIN_EMPLEADO.NEXTVAL, 'usuario', 'password', 'Cajero');
INSERT INTO LOGIN_EMPLEADO VALUES(SEQ_COD_LOGIN_EMPLEADO.NEXTVAL, 'MartaAV', 'AAA258', 'Cajero');
INSERT INTO LOGIN_EMPLEADO VALUES(SEQ_COD_LOGIN_EMPLEADO.NEXTVAL, 'Ñm123', '123', 'Asesor');
INSERT INTO LOGIN_EMPLEADO VALUES(SEQ_COD_LOGIN_EMPLEADO.NEXTVAL, 'ImU16', '321', 'Asesor');
INSERT INTO LOGIN_EMPLEADO VALUES(SEQ_COD_LOGIN_EMPLEADO.NEXTVAL, '24NvW', 'abcde', 'Asesor');
 
 