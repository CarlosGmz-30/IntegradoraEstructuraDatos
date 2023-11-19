DROP DATABASE IF EXISTS TAREAS;
CREATE DATABASE TAREAS;
USE TAREAS;
create table tarea
(
    id_tarea    int auto_increment
        primary key,
    titulo      varchar(32) not null,
    descripcion varchar(150) null,
    prioridad   varchar(10)  null,
    estado      varchar(16) null
);

DELIMITER $$
CREATE PROCEDURE agregar_tarea(
    IN titulo VARCHAR(32),
    IN descripcion VARCHAR(150),
    IN prioridad VARCHAR(10),
    IN estado VARCHAR(16))
BEGIN
    INSERT INTO tarea(titulo,descripcion,prioridad,estado)
    VALUES (titulo,
            descripcion,
            prioridad,
            estado
           );
END
$$
DELIMITER ;

call agregar_tarea('Investigacion','Investigacion sobre JWT','media','pendiente');

select *
from tarea;