 DROP DATABASE IF EXISTS TAREAS;
 CREATE DATABASE TAREAS;
 USE TAREAS;

create table tarea
(
    id_tarea    int auto_increment
        primary key,
    titulo      varchar(16) not null,
    descripcion varchar(32) null,
    prioridad   varchar(5)  null,
    estado      varchar(11) null
);

