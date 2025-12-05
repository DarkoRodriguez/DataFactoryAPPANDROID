Proyecto DataFactory
Integrantes:
Benjamin Dattoli,
Felix Omeñaca,
Darko Rodriguez

La Aplicacion tiene perfiles por parte del cliente, que esta dedicado mayoritariamente a la compra de productos, y por parte de la vista del admin, se pueden hacer operaciones de CRUD a los productos, cambiar el estado de las ordenes, ver los usuarios existentes, etc.

La API esta hosteada en RailWay, el link de esta es: https://datafactory-api.up.railway.app/
Estos son los endpoints de la API:
/api/v1/carrito_item
/api/v1/orden
/api/v1/orden_item
/api/v1/producto
/api/v1/usuarios
Cada endpoint tiene GET, GET/id, POST, PUT/id, y DELETE/ID

Para Ejecutar solo se debe Compilar e iniciar la app, se podran registrar y esto les dara acceso a un usuario de tipo "Cliente" con sus respectivas funcionalidades. Los usuarios de tipo admin se asignan de manera interna, pero dejare el siguiente admin a disposicion:
Login:
Email: fe@gmail.com
Password: Hola123$

En el directorio al principio se encuentra el apk de la aplicacion
