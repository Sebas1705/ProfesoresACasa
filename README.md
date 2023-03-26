# ProfesoresACasa

Proyecto público para trabajo de Desarrollo de Aplicaciones Distribuidas en la Universidad Rey Juan Carlos. Aplicación web que gestiona la contratación de profesores particulares a domicilio por parte de los cliente, y la publicación de ofertas por parte de los profesores. Para simplificar la base de datos y el numero de entidades el profesor actua a la vez de alumno, es decir que un usuario puede publicar ofertas y contratarlas al mismo tiempo.

## Fase 1 

### Funcionalidades:
* Privadas:
  - Editar y ver la información del usuario.
  - Gestionar nuestros contratos.
  - Publicar ofertas de profesor.
  - Contratar ofertas de profesores.

* Públicas:
  - Registro de usuario.
  - Ver información reducida de profesores.
  - Explorar las ofertas.
  - Denunciar irregularidades.


### Entidades:
- Usuario
- Publicación
- Contratación
- Denuncia

### Funcionalidades del servicio interno:
- Envio de correos de notificación al usuario: Denuncia, Publicación, Contratación

## Fase 2

### Cambios funcionalidades:
* Privadas:
  - Editar y ver la información del usuario. (igual)
  - Gestionar nuestros contratos. (igual)
  - Publicar ofertas de profesor. (igual)
  - Contratar ofertas de profesores. (igual)
  - Denunciar posts (antes publico y regularidades)

* Públicas:
  - Registro de usuario.
  - Ver información de profesores. (antes reducida)
  - Explorar las ofertas.
  
  ### Nueva entidades:
  - Ranking

### Capturas de los diagramas de navegación, clases y entidad-relación

* Navegación

![navegacion](https://github.com/Sebas1705/ProfesoresACasa/blob/0255de7a38de9a10f00ed54e1423925ff469cf63/assets/navegacion.png)

* Diagrama de clases

![clases](https://github.com/Sebas1705/ProfesoresACasa/blob/baabaf14d160bf022c64a0d6b54a8db999c25f5a/diagrama_clases.png)

* Diagrama modelo entidad-relacion

![entidad](https://github.com/Sebas1705/ProfesoresACasa/blob/baabaf14d160bf022c64a0d6b54a8db999c25f5a/entidad-relacion.png)

### Capturas de navegación
* Home
  -Aquí podemos ver sin iniciar sesión los post disponibles.
![Home](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Home.png)

* Login
  -Página de inicio de sesión
![Login](https://github.com/Sebas1705/ProfesoresACasa/blob/de766cbca664a1e8a9287ce5fb02c464d2c545d4/Navegacion/Login.png)

* Registro
  -Página de registro con formulario normal.
![Resgistro](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Registro.png)

* Inicio de sesión
  -Pantalla que muestra lo que se vería al inciar sesión con nuestro usuario
  ![menú](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Resgistrado.png)
  
 * Publicaciones
  -Pantalla que muestra mis publicaciones
  ![Publicaciones](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Publicaciones.png)
  
 * VerPost
  - Se puede ver un post en detalle, donde puedes contratar, denunciar, etc...
 ![Post](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Ver_Post.png)
 
 * Contratar
  -Acceso a contratar
 ![Contratar](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Contratar.png)
 
 * Denunciar
  -Acceso a denunciar
 ![Denunciar](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Denunciar.png)
 
 * Cambio de página
  -Se muestra como se ha pasado a la página 1
 ![Cambio](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Cambio_de_pagina.png)
 
 
 ## Fase 3
 
 
### Diagrama de clases y relación con templates

*UML
  -Diagrama ordenado, con leyenda de colores. 
  -Amarillo-->Controller; Morado-->Repository; Azul-->Seguridad; Verde-->Model; Naranja-->Config; Rosa-->Servicios
  ![UML](https://github.com/Sebas1705/ProfesoresACasa/blob/9a6b259d93bc74b73826a9f0a50e5348dc642bfc/assets/UML.png)

*Relación con templates
  -Esquema con los controladores y las templates que relaciona.
  ![Templates](https://github.com/Sebas1705/ProfesoresACasa/blob/9a6b259d93bc74b73826a9f0a50e5348dc642bfc/assets/Templates.png)
  
  
##Instrucciones para desplegar la aplicación


 -En primer lugar, generamos el rar de ambos proyectos desde la consola, con el comando mvn packge desde la carpeta main de cada proyecto.
 -Una vez generados los jar los subiremos a openstack, ejecutaremos desde terminal el comando scp -i la ruta dónde se encuentra nuestra clave privada ubuntu@ip:/home/ubuntu la última ruta puede ser cualquiera en la que tengamos permisos de ejecución. 
 -Posteriormente nos vamos a conectar a openStack con nuestras credenciales con el comando ssh -i ruta donde tenemos el certificado de nuestra clave privada + ubuntu@ipInstancia
 -Posteriormente, necesitaremos dos consolas para ejecutar ambas aplicaciones. Primero en una, ejecutaremos los siguientes comandos para instalar los paquetes necesarios. 
-JDK: sudo apt install default-jre
-MySQL-server: sudo apt install mysql-server
-Config mysql: sudo mysql
-CREATE USER 'test'@'localhost' IDENTIFIED BY 'test.profesoresACasa';
-GRANT ALL PRIVILEGES ON *.* TO 'test'@'localhost' WITH GRANT OPTION;
-CREATE SCHEMA profesoresACasa;
-exit
-Rabbitmq: sudo apt-get install rabbitmq-server
-Una vez tengamos esto instalado, en ambas consolas ejecutaremos java -jar profesores_a_casa-0.0.1-SNAPSHOT.jar y en la otra java -jar servicio_correo-0.0.1-SNAPSHOT.jar
-Cuando busquemos en internet ip de la instancia seguida de :8443, estará nuestra aplicación corriendo. 
-En la instancia hemos añadido un grupo de seguridad TCP de entrada con puerto 8443.
