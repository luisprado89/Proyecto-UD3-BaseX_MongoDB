# ACCESO A DATOS — PROYECTO 401
## MongoDB + BaseX

> [!WARNING]
> ## ENTREGA
> El nombre del fichero será **401.zip** y contendrá:
> - `mongodb.js`: archivo que contiene el código para generar la base de datos **MongoDB**.
> - `basex.xml`: archivo XML para generar la base de datos en **BaseX**.
> - El código de la aplicación. Solo será necesario la carpeta `src`, es decir, el directorio raíz de los paquetes con los archivos `.java`.


> [!INFO]
> ## A TENER EN CUENTA
> Se debe **crear un menú** que permita identificar, de forma clara y concisa, cada una de las acciones que se piden implementar.
>
> Además, **todos los mensajes** que se muestren (información, introducción de datos o errores) deberán ser **claros y concisos**.
>
> Cualquier código que:
> - Se detecte que es una copia
> - No compile ni ejecute
>
> **No será corregido y su puntuación será de 0**. 
> Independientemente de lo que se haya hecho.
> 
> Se deberá intentar obtener el resultado directamente con una consulta en XQuery o en MongoDB.  
> **Utilizar Java para obtener el resultado cuando existe una posible consulta será penalizado.**


## DESCRIPCIÓN DEL PROYECTO

Se pide desarrollar una plataforma de comercio electrónico que permita a los usuarios explorar productos, realizar pedidos y gestionar sus cuentas. La aplicación combinará el uso de dos bases de datos:
- Una **base de datos estática con datos XML** (en BaseX), llamada `productos`, para almacenar información estática sobre los productos.


> [!INFO]
> ## EJEMPLO DE XML
> Aquí podéis descargar un ejemplo válido de fichero XML que se añadirá en la base de datos BaseX.
>
> ```xml
> <productos>
>   <producto>
>     <id>1</id>
>     <nombre>Laptop HP Pavilion</nombre>
>     <descripcion>
>       Portátil con procesador Intel Core i5, 8 GB de RAM y 512 GB de SSD.
>     </descripcion>
>     <precio>799.99</precio>
>     <disponibilidad>10</disponibilidad>
>     <categoria>Electrónicos</categoria>
>     <fabricante>HP</fabricante>
>     <especificaciones>
>       <pantalla>15.6 pulgadas FHD</pantalla>
>       <sistema_operativo>Windows 10</sistema_operativo>
>       <puertos>
>         <puerto>USB-C</puerto>
>         <puerto>USB 3.0</puerto>
>       </puertos>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>2</id>
>     <nombre>Smartphone Samsung Galaxy S21</nombre>
>     <descripcion>
>       Smartphone Android con pantalla de 6.2 pulgadas, cámara triple y 128 GB de almacenamiento.
>     </descripcion>
>     <precio>899.99</precio>
>     <disponibilidad>15</disponibilidad>
>     <categoria>Teléfonos</categoria>
>     <fabricante>Samsung</fabricante>
>     <especificaciones>
>       <pantalla>6.2 pulgadas AMOLED</pantalla>
>       <camara>Triple cámara de 12MP + 12MP + 64MP</camara>
>       <bateria>4000 mAh</bateria>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>3</id>
>     <nombre>Tablet Lenovo Tab M10</nombre>
>     <descripcion>
>       Tablet con pantalla de 10.1 pulgadas, procesador Quad-Core y 32 GB de almacenamiento.
>     </descripcion>
>     <precio>199.99</precio>
>     <disponibilidad>20</disponibilidad>
>     <categoria>Tablets</categoria>
>     <fabricante>Lenovo</fabricante>
>     <especificaciones>
>       <pantalla>10.1 pulgadas IPS</pantalla>
>       <sistema_operativo>Android 9.0</sistema_operativo>
>       <almacenamiento>32 GB</almacenamiento>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>4</id>
>     <nombre>Auriculares Inalámbricos Sony WH-1000XM4</nombre>
>     <descripcion>
>       Auriculares con cancelación de ruido, Bluetooth y hasta 30 horas de batería.
>     </descripcion>
>     <precio>299.99</precio>
>     <disponibilidad>25</disponibilidad>
>     <categoria>Audio</categoria>
>     <fabricante>Sony</fabricante>
>     <especificaciones>
>       <cancelacion_ruido>Sí</cancelacion_ruido>
>       <conectividad>Bluetooth</conectividad>
>       <autonomia_bateria>30</autonomia_bateria>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>5</id>
>     <nombre>Televisor Samsung QLED Q80T</nombre>
>     <descripcion>
>       Televisor 4K con tecnología Quantum Dot y pantalla de 55 pulgadas.
>     </descripcion>
>     <precio>1299.99</precio>
>     <disponibilidad>8</disponibilidad>
>     <categoria>Electrónicos</categoria>
>     <fabricante>Samsung</fabricante>
>     <especificaciones>
>       <resolucion>4K UHD</resolucion>
>       <tecnologia>Quantum Dot</tecnologia>
>       <tamaño_pantalla>55 pulgadas</tamaño_pantalla>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>6</id>
>     <nombre>Cámara DSLR Canon EOS 80D</nombre>
>     <descripcion>
>       Cámara réflex digital con sensor APS-C de 24.2 MP y grabación de video Full HD.
>     </descripcion>
>     <precio>1099.99</precio>
>     <disponibilidad>12</disponibilidad>
>     <categoria>Fotografía</categoria>
>     <fabricante>Canon</fabricante>
>     <especificaciones>
>       <megapixeles>24.2 MP</megapixeles>
>       <grabacion_video>Full HD</grabacion_video>
>       <tipo_sensor>APS-C</tipo_sensor>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>7</id>
>     <nombre>Aspiradora Robot iRobot Roomba 960</nombre>
>     <descripcion>
>       Aspiradora robot con tecnología de navegación visual y control mediante aplicación.
>     </descripcion>
>     <precio>499.99</precio>
>     <disponibilidad>18</disponibilidad>
>     <categoria>Hogar</categoria>
>     <fabricante>iRobot</fabricante>
>     <especificaciones>
>       <tecnologia_navegacion>Visual</tecnologia_navegacion>
>       <control_app>Sí</control_app>
>       <autonomia_bateria>75 minutos</autonomia_bateria>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>8</id>
>     <nombre>Impresora 3D Creality Ender 3</nombre>
>     <descripcion>
>       Impresora 3D de nivel de entrada con área de impresión de 220 x 220 x 250 mm.
>     </descripcion>
>     <precio>249.99</precio>
>     <disponibilidad>10</disponibilidad>
>     <categoria>Tecnología</categoria>
>     <fabricante>Creality</fabricante>
>     <especificaciones>
>       <area_impresion>220 x 220 x 250 mm</area_impresion>
>       <tecnologia>FFF (Fused Filament Fabrication)</tecnologia>
>       <conectividad>USB, Tarjeta SD</conectividad>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>9</id>
>     <nombre>Bicicleta de Montaña Trek X-Caliber 9</nombre>
>     <descripcion>
>       Bicicleta de montaña con cuadro de aluminio y transmisión Shimano Deore 1x12.
>     </descripcion>
>     <precio>1299.99</precio>
>     <disponibilidad>7</disponibilidad>
>     <categoria>Deportes</categoria>
>     <fabricante>Trek</fabricante>
>     <especificaciones>
>       <material_cuadro>Aluminio</material_cuadro>
>       <transmision>Shimano Deore 1x12</transmision>
>       <tipo_freno>Freno de disco hidráulico</tipo_freno>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>10</id>
>     <nombre>Horno de Convección KitchenAid</nombre>
>     <descripcion>
>       Horno de convección de acero inoxidable con capacidad para 12 pizzas.
>     </descripcion>
>     <precio>449.99</precio>
>     <disponibilidad>15</disponibilidad>
>     <categoria>Electrodomésticos</categoria>
>     <fabricante>KitchenAid</fabricante>
>     <especificaciones>
>       <capacidad>12 pizzas</capacidad>
>       <tipo>Convección</tipo>
>       <control>Electrónico</control>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>11</id>
>     <nombre>Altavoces Bluetooth JBL Flip 5</nombre>
>     <descripcion>
>       Altavoces portátiles Bluetooth resistentes al agua con hasta 12 horas de reproducción.
>     </descripcion>
>     <precio>99.99</precio>
>     <disponibilidad>20</disponibilidad>
>     <categoria>Audio</categoria>
>     <fabricante>JBL</fabricante>
>     <especificaciones>
>       <resistencia_agua>Sí</resistencia_agua>
>       <autonomia_bateria>12</autonomia_bateria>
>       <conectividad>Bluetooth 4.2</conectividad>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>12</id>
>     <nombre>Silla de Oficina Ergonómica</nombre>
>     <descripcion>
>       Silla de oficina con respaldo ajustable, apoyabrazos y soporte lumbar.
>     </descripcion>
>     <precio>179.99</precio>
>     <disponibilidad>25</disponibilidad>
>     <categoria>Muebles</categoria>
>     <fabricante>OfiComfort</fabricante>
>     <especificaciones>
>       <respaldo_ajustable>Sí</respaldo_ajustable>
>       <apoyabrazos>Ajustables</apoyabrazos>
>       <soporte_lumbar>Sí</soporte_lumbar>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>13</id>
>     <nombre>Teclado Mecánico RGB Logitech G Pro X</nombre>
>     <descripcion>
>       Teclado mecánico para juegos con interruptores intercambiables y retroiluminación RGB.
>     </descripcion>
>     <precio>149.99</precio>
>     <disponibilidad>15</disponibilidad>
>     <categoria>Tecnología</categoria>
>     <fabricante>Logitech</fabricante>
>     <especificaciones>
>       <interruptores>Intercambiables</interruptores>
>       <retroiluminacion>RGB</retroiluminacion>
>       <conectividad>USB</conectividad>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>14</id>
>     <nombre>Cafetera Espresso Breville Barista Express</nombre>
>     <descripcion>
>       Cafetera semiautomática con molinillo integrado para preparar café espresso de alta calidad.
>     </descripcion>
>     <precio>699.99</precio>
>     <disponibilidad>10</disponibilidad>
>     <categoria>Electrodomésticos</categoria>
>     <fabricante>Breville</fabricante>
>     <especificaciones>
>       <tipo>Cafetera semiautomática</tipo>
>       <molinillo_integrado>Sí</molinillo_integrado>
>       <presion>15 bares</presion>
>     </especificaciones>
>   </producto>
>
>   <producto>
>     <id>15</id>
>     <nombre>Monitor Gaming ASUS ROG Swift PG279Q</nombre>
>     <descripcion>
>       Monitor de 27 pulgadas con resolución 1440p, frecuencia de actualización de 165 Hz y tecnología G-Sync.
>     </descripcion>
>     <precio>799.99</precio>
>     <disponibilidad>8</disponibilidad>
>     <categoria>Electrónicos</categoria>
>     <fabricante>ASUS</fabricante>
>     <especificaciones>
>       <tamaño_pantalla>27 pulgadas</tamaño_pantalla>
>       <resolucion>1440p</resolucion>
>       <frecuencia_actualizacion>165 Hz</frecuencia_actualizacion>
>       <tecnologia>G-Sync</tecnologia>
>     </especificaciones>
>   </producto>
> </productos>
> ```


- Una base de datos MongoDB, llamada `tienda` para gestionar detalles dinámicos como pedidos, clientes y carritos de compras.


> [!INFO]
> ## POSIBLE ESTRUCTURA DE LA BASE DE DATOS MONGO
> A modo de ayuda y posible idea, sería conveniente crear tres colecciones:
> - `Clientes`: almacena la información personal de cada cliente.
> - `Carrito`: contiene la lista de productos que el usuario está mirando de comprar.
> - `Pedidos`: son los productos que el usuario ha comprado.
>
> Aquí tenéis un ejemplo de posible estructura. Aunque lo haya puesto en un solo fichero es recomendable gestionarlo en tres colecciones independientes aun así, cada uno puede implementarlo como prefiera.
>
> Echadle un vistazo a la [sección sobre las estructuras de los documentos](https://mp0486-adp.vercel.app/docs/unidades/03/contenidos/mongo/estructura-documento) en donde se habla de las diferentes formas de enlazar colecciones en MongoDB.
>
> ```json
> {
>   "_id": ObjectId(),
>   "nombre": "Nombre Cliente 1",
>   "email": "cliente1@example.com",
>   "direccion": "Dirección Cliente 1",
>   "carrito": [
>     {
>       "producto_id": 1,
>       "nombre": "Laptop HP Pavilion",
>       "cantidad": 2,
>       "precio_unitario": 799.99
>     },
>     {
>       "producto_id": 3,
>       "nombre": "Tablet Lenovo Tab M10",
>       "cantidad": 1,
>       "precio_unitario": 199.99
>     }
>   ],
>   "pedidos": [
>     {
>       "pedido_id": ObjectId(),
>       "productos": [
>         {
>           "producto_id": 1,
>           "nombre": "Laptop HP Pavilion",
>           "cantidad": 2,
>           "precio_unitario": 799.99
>         }
>       ],
>       "total": 1599.98,
>       "fecha_pedido": ISODate("2023-01-01T12:30:00.000Z")
>     }
>   ]
> }
> ```



## Tareas a realizar

Se pide implementar un menú que permita realizar las siguientes operaciones sobre la base de datos BaseX y MongoDB.  
Una posible estructura de menú puede ser la siguiente:

```text
CONSULTAS BASE DE DATOS XML

1. DESCRIPCIÓN DE LA OPERACIÓN 1
2. DESCRIPCIÓN DE LA OPERACIÓN 2
...
7. DESCRIPCIÓN DE LA OPERACIÓN 7


CONSULTAS BASE DE DATOS MONGODB

8. DESCRIPCIÓN DE LA OPERACIÓN 8
...
17. DESCRIPCIÓN DE LA OPERACIÓN 17
```

En cuanto a las operaciones que se deberán realizar, serán las siguientes:

### Sobre la base de datos XML se pide:

1. Modificar el valor de un elemento de un XML según un ID.
2. Eliminar un `producto` según su ID.
3. **Consulta 1**: Obtener todos los productos por orden alfabético del nombre (se mostrarán los siguientes campos: `id`, `nombre`, `precio`, `disponibilidad` y `categoria`).
4. **Consulta 2**: Listar productos con una disponibilidad mayor a X unidades (se mostrarán los siguientes campos: `id`, `nombre`, `precio`, `disponibilidad` y `categoria`).
5. **Consulta 3**: Mostrar la `categoria`, el `nombre` y el `precio` del producto más caro para cada `categoria`.  
   En el caso de haber varios se devolverá el de la primera posición.
6. **Consulta 4**: Mostrar el `nombre` de los productos y su `fabricante` para aquellos productos cuya `descripcion` incluya una subcadena.  
   Se deberá mostrar la información ordenada según el nombre del `fabricante` de forma inversa al alfabeto.
7. **Consulta 5**: Mostrar la cantidad total de productos en cada `categoria` (teniendo en cuenta el elemento `disponibilidad`) y calcular el porcentaje que representa respecto al total de productos.


> [!WARNING]
> ## NOTA SOBRE LAS CONSULTAS ANTERIORES
> Todas las consultas anteriores tienen una consulta **XQuery** que permite obtener el resultado de forma directa.


### Sobre la base de datos MongoDB se pide:

8. Crear un nuevo cliente (no podrá haber `email` repetidos).
9. Identificar cliente según el `email`.  
   Dado el email se obtendrá el `ID del cliente` de forma que las siguientes consultas se harán sobre ese cliente.  
   Para cambiar de cliente se tendrá que volver a seleccionar esta opción.
10. Borrar un cliente.
11. Modificar el valor de un campo de la información del cliente.
12. Añadir producto al carrito del cliente.  
    Se pedirá: `id del producto` y `cantidad`, así como si se desea seguir introduciendo más productos.
13. Mostrar el carrito del cliente.  
    Se mostrarán los datos del carrito y el precio total.
14. Mostrar pedidos del cliente.
15. Pagar el carrito de un cliente:  
    se mostrará el carrito junto con una orden de confirmación.  
    Si la orden es positiva se pasarán todos los productos a formar parte de un nuevo pedido.
16. **Consulta 1**: Teniendo en cuenta todos los clientes, calcular el total de la compra para cada carrito y listar los resultados ordenados por el total de forma ascendente. (No es necesario tener en cuenta la multiplicación de `precio_unitario` * `cantidad` con sumar los `precio_unitario` es suficiente).
17. **Consulta 2**: Teniendo en cuenta todos los clientes, obtener el total gastado por cada cliente en todos sus pedidos.



> [!INFO]
> ## NOTA SOBRE LOS APARTADOS ANTERIORES
> Para la realización de los **apartados 14, 16 y 17**:
> - Si se ha diseñado la BD con una única colección existe una consulta en **MongoDB** que permite obtener el resultado de forma directa.
> - Si se ha diseñado la BD utilizando varias colecciones se puede usar Java para obtener los datos de una colección para añadirlos a la consulta de la otra colección. Pero no para formatear o calcular los datos finales.
