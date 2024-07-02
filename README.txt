Hola muy buenos días, tardes o noches, según la hora en que esté siendo leído este documento.
El presente proyecto fue desarrollado por Harrison Daniel Ramírez Burgos como una prueba técnica para aplicar
al puesto de developer en la empresa CobisTopaz.

Para poder ejecutar dicho proyecto hay que tener en cuenta las siguientes recomendaciones:

1. Hay que tener correctamente instalado Maven en el dispositivo, para comprobar esto se puede dirigir a una consola de comandos y
	ejecutar el comando "mvn -v", este comando funcionará siempre y cuando Maven se encuentre en el dispositivo. En caso de que no,
	hay que instalarlo.
2. Se debe tener instalado MySQL con la configuración por defecto, esto debido a que la configuración del proyecto apunta al motor
	de base de datos con dicha configuración (es decir, usuario "root" y sin contraseña preestablecida). Para esto se recomienda utilizar
	una máquina virtual, ya puede ser en VMWare Workstation Player o Oracle VirtualBox donde se haga una instalación limpia de MySQL desde cero,
	ya sea apoyandose por XAMPP, WAMPP o Laragon, esto es según preferencia.
3. Se deja adjunto el script para la creación de la base de datos con el fin de realizar la menor cantidad de trabajo posible. Los comandos
	en el archivo .sql solo deben ser copiados y pegados en el gestor para crear la base de datos correctamente y con la estructura
	que requiere el aplicativo para funcionar.
4. MySQL se debe estar ejecutando al momento de querer iniciar el proyecto. No es necesario al momento de compilarlo.

Compilación del proyecto:

Para poder compilar el proyecto se dejó un archivo ejecutable .bat que realiza 2 acciones:
	1. Crea un archivo para ejecutar el proyecto antes de compilar el mismo (esto ya que después de compilar el proyecto, la consola se cierra
		y no permite realizar ninguna otra acción).
	2. Compila el proyecto, generando un archivo ejecutable .jar en la carpeta target.

Para compilar y ejecutar el proyecto solo hace falta ir a la carpeta raíz del mismo y ejecutar el archivo "Compilar-proyecto.bat",
este no contiene ningún script malicioso y puede ser revisado por la persona en cuestión si lo encuentra necesario.

Si el proyecto fue compilado correctamente, se puede ejecutar el archivo "ejecutar_proyecto.bat" que también se encontrará en la carpeta
raíz. Tras esto, se abrirá una terminal que contiene los logs del aplicativo en ejecución.

Utilizar el aplicativo

Para poder utiilzar el aplicativo mediante Swagger, solo hay que dirigirse a la URL http://localhost:8080/swagger-ui/index.html,
donde se abrirá la documentación respectiva de la API con Swagger (OpenAPI 3.0).



Consideraciones generales de desarrollo

En primer lugar, al utilizar una infraestructura hexagonal para desarrollar el aplicativo, se hizo uso de 3 carpetas: application, domain e infraestructure.
Cada una cumple con las funciones de la capa que representa. Es decir, la capa de aplicación cumple casi en su totalidad con las funciones que va a realizar
el usuario, ya sea utilizar los servicios de autenticación o de usuarios, aplicar los filtros necesarios para la seguridad del aplicativo
mediante Spring Security entre otras funciones.
Por su parte, la capa de dominio se encarga de definir la estructura básica que todo el proyecto debería seguir. En esta capa se crearon
interfaces y clases usadas como un contrato general que toda la aplicación debe seguir, de esta forma se apoya al desacoplamiento de las 
clases y la eliminación de dependencias directas al usar interfaces.
Finalmente, la capa de infraestructura se encarga de contener a los controladores y los adaptadores de entrada y salida de datos, en este
caso los controladores (AuthController y UserController). También implementa las interfaces expuestas por la capa de aplicación para poder
comunicarse con la misma.
Se intentó dejar en la medida de lo posible, que toca la aplicación funcionara mediante interfaces autoinyectadas, de forma que
ninguna clase quedara demasiado acoplada a otra específicamente.



Muchas gracias por utilizar mi aplicativo y espero que pueda cumplir con las expectativas de quien lo va a probar/utilizar.

Att:
Harrison Daniel Ramírez Burgos
Tecnólogo en desarrollo de software.
