create database cobisTopazPrueba;

use cobisTopazPrueba;

create table user_tabla (id varchar(255) not null primary key, nombre_usuario varchar(255) not null unique, contrasena text not null, roles text not null );
