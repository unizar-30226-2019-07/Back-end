/* Crear BD y seleccionarla */
CREATE DATABASE IF NOT EXISTS PruebaSof CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE PruebaSof;

CREATE TABLE Usuario
(
idUsuario 			BIGINT 							PRIMARY KEY,
Sexo 				ENUM('hombre', 'mujer'),
Fecha_de_nacimiento DATE,
Ubicacion 			VARCHAR(100) 					NOT NULL,
Calificacion 		BIGINT NOT NULL,
Estado_de_la_cuenta ENUM('bloqueada', 'activa') 	NOT NULL,
Contraseña 			VARCHAR(100) 					NOT NULL,
email 				VARCHAR(100) 					NOT NULL,
Apellidos 			VARCHAR(100) 					NOT NULL,
Nombre 				VARCHAR(50) 					NOT NULL,
Tipo 				ENUM('administrador', 'usuario') NOT NULL
);

CREATE TABLE Informe
(
Descripcion 		VARCHAR(300) 					NOT NULL,
Estado_del_informe 	ENUM('pendiente', 'resuelto') 	NOT NULL,
idInforme 			BIGINT,
Fecha_de_realizacion DATE 							NOT NULL,
Asunto 				VARCHAR(30) 					NOT NULL,
idInformador 		BIGINT,
idEvaluado 			BIGINT,
PRIMARY KEY (idInforme,idInformador,idEvaluado),
FOREIGN KEY (idInformador) REFERENCES Usuario(idUsuario),
FOREIGN KEY (idEvaluado) REFERENCES Usuario(idUsuario)
);


CREATE TABLE Categoria
(
Nombre_Categoria 	VARCHAR(50) 					PRIMARY KEY
);


CREATE TABLE Anuncio
(
idProducto 			BIGINT,
Fecha_de_publicacion DATE 							NOT NULL,
Descripcion 		VARCHAR(300) 					NOT NULL,
Titulo 				VARCHAR(50) 					NOT NULL,
Ubicacion 			VARCHAR(100) 					NOT NULL,
Precio 				BIGINT 							NOT NULL,
Nfavoritos 			BIGINT 							NOT NULL,
Nvisitas 			BIGINT 							NOT NULL,
Usuario_idUsuario 	BIGINT,
Nombre_Categoria 	VARCHAR(30),
PRIMARY KEY (idProducto,Usuario_idUsuario),
FOREIGN KEY (Usuario_idUsuario) REFERENCES Usuario(idUsuario),
FOREIGN KEY (Nombre_Categoria) REFERENCES Categoria(Nombre_Categoria)
);

CREATE TABLE Subasta
(
Fecha_de_publicacion DATE 							NOT NULL,
idProducto 			BIGINT,
Descripcion 		VARCHAR(300) 					NOT NULL,
Titulo 				VARCHAR(50) 					NOT NULL,
Ubicacion 			VARCHAR(100) 					NOT NULL,
Fecha_de_Finalizacion DATE 							NOT NULL,
Precio_Salida 		BIGINT 							NOT NULL,
Usuario_idUsuario 	BIGINT,
Nombre_Categoria 	VARCHAR(30) 					NOT NULL,
PRIMARY KEY (idProducto,Usuario_idUsuario),
FOREIGN KEY (Usuario_idUsuario) REFERENCES Usuario(idUsuario),
FOREIGN KEY (Nombre_Categoria) REFERENCES Categoria(Nombre_Categoria)
);

CREATE TABLE Valoracion
(
idValoracion 		BIGINT,
Valor 				BIGINT							NOT NULL,
Comentario 			VARCHAR(300),
idVendedor 			BIGINT,
idComprador 		BIGINT,
idProducto 			BIGINT,
idAnunciante 		BIGINT,
PRIMARY KEY (idValoracion,idVendedor,idComprador),
FOREIGN KEY (idVendedor) REFERENCES Usuario(idUsuario),
FOREIGN KEY (idComprador) REFERENCES Usuario(idUsuario),
FOREIGN KEY (idProducto,idAnunciante) REFERENCES Anuncio(idProducto,Usuario_idUsuario),
FOREIGN KEY (idProducto,idAnunciante) REFERENCES Subasta(idProducto,Usuario_idUsuario)
);

CREATE TABLE Imagen
(
idImagen 			BIGINT,
Nombre 				VARCHAR(100) 					NOT NULL,
Tipo 				VARCHAR(10) 					NOT NULL,
Tamaño 				BIGINT 							NOT NULL,
Contenido 			LONGBLOB 						NOT NULL,
Usuario_creador 	BIGINT,
idProducto			BIGINT,
PRIMARY KEY (idImagen,idProducto,Usuario_creador),
FOREIGN KEY (Usuario_creador) REFERENCES Usuario(idUsuario),
FOREIGN KEY (idProducto,Usuario_creador) REFERENCES Anuncio(idProducto,Usuario_idUsuario),
FOREIGN KEY (idProducto,Usuario_creador) REFERENCES Subasta(idProducto,Usuario_idUsuario)
);

CREATE TABLE Participa
(
Puja 				BIGINT 							NOT NULL,
Usuario_idUsuario 	BIGINT,
Subasta_idProducto 	BIGINT,
Subasta_idUsuario 	BIGINT,
PRIMARY KEY (Usuario_idUsuario,Subasta_idProducto,Subasta_idUsuario),
FOREIGN KEY (Usuario_idUsuario) REFERENCES Usuario(idUsuario),
FOREIGN KEY (Subasta_idProducto,Subasta_idUsuario) REFERENCES Subasta(idProducto,Usuario_idUsuario)
);

CREATE TABLE Chat
(
idChat 				BIGINT,
idAnunciante 		BIGINT,
idCliente 			BIGINT,
Anuncio_idProducto 	BIGINT,
Anuncio_idUsuario  	BIGINT,
PRIMARY KEY (idChat,idAnunciante,idCliente,Anuncio_idProducto,Anuncio_idUsuario),
FOREIGN KEY (idAnunciante) REFERENCES Usuario(idUsuario),
FOREIGN KEY (idCliente) REFERENCES Usuario(idUsuario),
FOREIGN KEY (Anuncio_idProducto,Anuncio_idUsuario) REFERENCES Anuncio(idProducto,Usuario_idUsuario)
);

CREATE TABLE Mensaje
(
Contenido 			VARCHAR(300) 					NOT NULL,
Estado 				ENUM('enviado', 'recibido') 	NOT NULL,
Fecha_de_envio 		DATE 							NOT NULL,
idMensaje 			BIGINT,
Chat_idChat 		BIGINT,
idAnunciante 		BIGINT,
idCliente 			BIGINT,
Anuncio_idProducto 	BIGINT,
Anuncio_idUsuario  	BIGINT,
Usuario_idUsuario 	BIGINT,
PRIMARY KEY (idMensaje,Chat_idChat,idAnunciante,idCliente,Anuncio_idProducto,Anuncio_idUsuario,Usuario_idUsuario),
FOREIGN KEY (Chat_idChat,idAnunciante,idCliente,Anuncio_idProducto,Anuncio_idUsuario) REFERENCES Chat(idChat,idAnunciante,idCliente,Anuncio_idProducto,Anuncio_idUsuario),
FOREIGN KEY (Usuario_idUsuario) REFERENCES Usuario(idUsuario)
);
