CREATE TABLE `usertype` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `user` (
   `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
   `dni` varchar(20) NOT NULL, 
   `name` varchar(50) not null,
   `surname1` varchar(50),
   `surname2` varchar(50),
   `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
   `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
   `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
   `id_usertype` bigint(20) NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `cita` (
    `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `fecha` datetime DEFAULT NULL,
    `pagado` bit not null,
    `id_animal` bigint(20) not null,
    `id_servicio` bigint(20) not null,
    `id_usuario` bigint(20) not null
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `servicio` (
    `id` bigint(20) not null PRIMARY KEY AUTO_INCREMENT,
    `nombre` varchar(255) not null,
    `precio` varchar(255) not null,
    `imagen` bigint NOT NULL DEFAULT '1',
    `descripcion` varchar(255) not null,
    `id_tiposervicio` bigint(20) not null
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `tiposervicio` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `vacuna` (
   `id` bigint(20) not null PRIMARY KEY AUTO_INCREMENT,
   `nombre` varchar(255) not null,
   `id_tipoanimal` bigint(20) not null
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
    
CREATE TABLE `fechavac` (
    `id` bigint(20) not null PRIMARY KEY AUTO_INCREMENT,
    `fecha_inic` datetime default null,
    `id_vacuna` bigint(20) not null,
    `id_animal` bigint(20) not null
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
        
CREATE TABLE `tipoanimal` (
	`id` bigint(20) not null PRIMARY KEY AUTO_INCREMENT,
    `tipo` varchar(255) not null
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `animal` ( 
    `id` bigint(20) not null PRIMARY KEY AUTO_INCREMENT,
    `nombre_animal` varchar(255) not null,
    `color` varchar(80) not null,
    `raza` varchar(80) not null,
    `fecha_nac` datetime not null,
    `vacunado` bit not null,
    `peso` double (5,2),
    `id_tipoanimal` bigint(20) not null
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
   	

INSERT INTO `user` (`id`,`dni`,  `name`, `surname1`, `surname2`, `email`, `id_usertype`,  `username`, `password`) VALUES
(1, 'raimon','12345678W', 'vilar', 'morera', 'test@email.com', 1,  'raivi', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(2, 'alvaro','12345678Q', 'talaya', 'romance', 'test@email.com', 2,  'alta', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(3, 'mario','12345678E', 'tomas', 'zanon', 'test@email.com', 2,  'mato', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(4, 'aitana','12345678X', 'collado', 'soler', 'test@email.com', 2,  'aico', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(5, 'carlos','12345678Z', 'merlos', 'pilar', 'test@email.com', 2,  'came', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(6, 'luis','12345678D', 'perez', 'derecho', 'test@email.com', 2,  'lupe', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(7, 'estefania','12345678L', 'boriko', 'izquierdo', 'test@email.com', 2,  'esbo', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(8, 'quique','12345678P', 'aroca', 'garcia', 'test@email.com', 2,  'quiga', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(9, 'adrian','12345678O', 'duyang', 'liang', 'test@email.com', 2,  'adu', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(10, 'rafael','12345678R', 'aznar', 'aparici', 'test@email.com', 2,  'raza', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6');

INSERT INTO `usertype`(`id`,`name`) VAlUES (1, 'ADMIN'),(2, 'USER');

INSERT INTO `tipoanimal`(`id`,`tipo`) VAlUES (1, 'perro'),(2, 'gato'),(3,'conejo');

INSERT INTO `animal`(`id`,`nombre_animal`,`color`,`raza`,`fecha_nac`,`vacunado`,`peso`,`id_tipoanimal`) VAlUES (1, 'Juani','Marron','RogWailer','2021-11-11 23:12:20',1,23.5,1),
(2, 'Manol','Blanco','Pitbull','2015-11-11 23:12:20',1,20.5,1),
(3, 'Loki','Rojo','Pug','2017-11-11 23:12:20',1,19.5,1),
(4, 'Koko','Rosa','Pug','2019-11-11 23:12:20',1,12.5,1),
(5, 'Illa','Verde','Bulldog frances','2020-11-11 23:12:20',1,33.5,1),

INSERT INTO `animal`(`id`,`nombre_animal`,`color`,`raza`,`fecha_nac`,`vacunado`,`peso`,`id_tipoanimal`) VAlUES (6, 'Dodo','Marron','Egipcio','2021-11-11 23:12:20',0,23.5,2),
(7, 'Dado','Blanco','Orejon','2015-11-11 23:12:20',1,20.5,3),
(8, 'Didi','Gris','Peludo','2017-11-11 23:12:20',1,19.5,2),
(9, 'Kiko','Rosa','Enano','2019-11-11 23:12:20',1,12.5,3);

INSERT INTO `vacuna`(`id`,`nombre`,`id_tipoanimal`) VAlUES (1, 'Covid-19',1),(2, 'Vacteriana',3),(3,'Bultular',3), (4, 'Parvovirus canino',1),(5, 'Moquillo',2),(6,'Gripe felina',2);

INSERT INTO `fechavac`(`id`,`fecha_inic`,`id_vacuna`,`id_animal`) VAlUES (1, '1999-7-22 22:22:22',1,2),(2,'1999-7-22 11:11:11',2,7),(3,'1995-7-15 13:13:13',3,9), (4, '1992-9-15 10:10:10',4,4),(5, '1990-7-15 18:18:18',5,6),(6,'1985-1-15 13:13:13',6,8);

INSERT INTO `tiposervicio`(`id`,`name`) VAlUES (1, 'limpieza'),(2, 'peluqueria'),(3,'vacuna'),(4, 'paseo'),(5, 'guarderia'),(6,'medico');

INSERT INTO `servicio`(`id`,`nombre`,`precio`,`imagen`,`descripcion`,`id_tiposervicio`) VAlUES (1, 'limpieza de uñas',7,1,'Limpiado de uñas del animal limado y cortado',1),
(2, 'Cortado de pelo',10,1,'Recorte del pelo ya que es verano y asi va mas comodo',2),
(3, 'Paseo por el parque',15,0,'Paseo de 1 hora con los diferentes perros del veterinario',4),
(4, 'Guarderia de cachorro',9.5,1,'Cachorro cuidado en la guarderia de la veterinaria con los diferentes cachorros',5);

INSERT INTO `cita`(`id`,`fecha`,`pagado`,`id_animal`,`id_servicio`,`id_usuario`) VAlUES (1, '2022-10-10 22:22:22',1,1,1,1);

PARA HACER UN UPDATE LO QUE NECESITAS:
{
    "id": 42,
    "dni": "12345678Z",
    "name": "pelopossnesooooo",
    "surname1": "poleo",
    "surname2": "moloni",
    "username": "prueba",
    "email": "pepe@pepe112.com",
    "usertype": {
        "id": 2
    }
}