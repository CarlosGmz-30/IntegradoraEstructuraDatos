DROP DATABASE IF EXISTS TAREAS;
CREATE DATABASE TAREAS;
USE TAREAS;
create table tarea
(
    id_tarea    int auto_increment primary key,
    titulo      varchar(32)  not null,
    descripcion varchar(150) not null,
    fecha       varchar(50)  not null,
    prioridad   varchar(10)  not null,
    estado      varchar(16)  not null default 'pendiente'
);

DELIMITER $$
CREATE PROCEDURE agregar_tarea(
    IN titulo VARCHAR(32),
    IN descripcion VARCHAR(150),
    IN fecha VARCHAR(50),
    IN prioridad VARCHAR(10),
    IN estado VARCHAR(16))
BEGIN
    INSERT INTO tarea(titulo, descripcion, fecha, prioridad, estado)
    VALUES (titulo,
            descripcion,
            fecha,
            prioridad,
            estado);
END
$$
DELIMITER ;


select * from tarea;