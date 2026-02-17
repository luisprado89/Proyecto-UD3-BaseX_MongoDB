# 📦 Proyecto 401 — BaseX XML + MongoDB
## Plataforma de comercio electrónico
*(README personal de verificación y control del proyecto)*

---

## 1️⃣ Contexto del proyecto

Este proyecto corresponde a la actividad **401**, cuyo objetivo es desarrollar una **plataforma de comercio electrónico** combinando dos bases de datos:

- **BaseX (XML)** → datos estáticos de productos
- **MongoDB** → datos dinámicos (clientes, carritos y pedidos)

Este README **NO está pensado para el profesor**, sino como **documento personal** para:

- Verificar que **todo el enunciado está cumplido**
- Tener evidencia clara de **pruebas reales ejecutadas**
- Confirmar que no queda **ningún apartado sin probar**
- Poder revisar rápidamente el proyecto antes de la entrega

---

## 2️⃣ Requisitos de entrega (según enunciado)

El archivo entregado debe ser **401.zip** y contener:

- `mongodb.js` → script para generar la base de datos MongoDB
- `basex.xml` → archivo XML cargado en BaseX (BD `productos`)
- `src/` → código Java completo (solo la carpeta `src`)

### Condiciones importantes del enunciado

- El código **debe compilar y ejecutarse**
- Cualquier código copiado → **0 puntos**
- Si no ejecuta → **0 puntos**
- Siempre que exista una consulta directa en:
    - **XQuery** o
    - **MongoDB**

  👉 usarla directamente  
  👉 usar Java solo cuando sea estrictamente necesario  
  👉 usar Java para formatear o calcular resultados finales **penaliza**

✔️ Todo esto se cumple en el proyecto.

---

## 3️⃣ Descripción general del sistema

### 🔹 Base de datos XML (BaseX)

- Nombre: `productos`
- Contiene información **estática** de los productos:
    - `id`
    - `nombre`
    - `descripcion`
    - `precio`
    - `disponibilidad`
    - `categoria`
    - `fabricante`
    - `especificaciones` (nodo XML completo)

Se utiliza **XQuery** para todas las consultas solicitadas.

---

### 🔹 Base de datos MongoDB

- Nombre: `tienda`
- Colecciones:
    - `clientes`
    - `carritos`
    - `pedidos`

📌 **Requisito clave cumplido**:  
Los productos añadidos al carrito y pedidos **referencian el ID del producto del XML**, no se duplican los datos del producto.

---

## 4️⃣ Menú implementado

### CONSULTAS BASE DE DATOS XML
1. Modificar valor de un elemento según ID
2. Eliminar producto según ID
3. Consulta 1: Productos ordenados por nombre
4. Consulta 2: Productos con disponibilidad > X
5. Consulta 3: Producto más caro por categoría
6. Consulta 4: Buscar productos por descripción (subcadena)
7. Consulta 5: Stock total y porcentaje por categoría

### CONSULTAS BASE DE DATOS MONGODB
8. Crear un nuevo cliente
9. Identificar cliente por email
10. Borrar cliente
11. Modificar campo del cliente
12. Añadir producto al carrito
13. Mostrar carrito
14. Mostrar pedidos
15. Pagar carrito / crear pedido
16. Consulta agregada: total carrito por cliente
17. Consulta agregada: total gastado por cliente

---

## 5️⃣ Evaluación apartado por apartado (pruebas reales)

---

## 🟢 BASE DE DATOS XML (Apartados 1–7)

### ✅ Apartado 1 — Modificar valor de un elemento según ID (0,10)

✔️ Probado:
- Modificar `nombre`
- Modificar `precio`
- Modificar `disponibilidad`
- Modificar nodo completo `<especificaciones>`
- Error controlado con ID inexistente (999)

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 2 — Eliminar producto según ID (0,10)

✔️ Probado:
- Eliminación correcta (ID 48)
- Error controlado con ID inexistente (99999)

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 3 — Consulta 1: productos ordenados por nombre (0,05)

✔️ Probado múltiples veces  
✔️ Orden alfabético visible y correcto

➡️ **PUNTUACIÓN: 0,05 / 0,05**

---

### ✅ Apartado 4 — Consulta 2: disponibilidad > X (0,05)

✔️ Probado con distintos valores (ej. X = 20)  
✔️ Cambios reflejados tras modificar disponibilidad

➡️ **PUNTUACIÓN: 0,05 / 0,05**

---

### ✅ Apartado 5 — Consulta 3: producto más caro por categoría (0,05)

✔️ Probado correctamente  
✔️ **Empate demostrado explícitamente**:
- Categoría *Teléfonos*
- IDs **2 y 46** con precio **9999.99**
  ✔️ Devuelve solo uno → correcto por uso de `[1]`

➡️ **PUNTUACIÓN: 0,05 / 0,05**

---

### ✅ Apartado 6 — Consulta 4: búsqueda por subcadena (0,10)

✔️ Probado con:
- `"android"`
- `"cámara"`
  ✔️ Orden por fabricante **Z–A** correcto

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 7 — Consulta 5: stock total y porcentaje (0,10)

✔️ Sumas correctas  
✔️ Porcentajes coherentes (≈ 100%)

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

## 🟢 BASE DE DATOS MONGODB (Apartados 8–17)

### ✅ Apartado 8 — Crear cliente (0,10)

✔️ Cliente creado correctamente  
✔️ Error por email duplicado (índice único)

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 9 — Identificar cliente por email (0,10)

✔️ Cliente activo cambia correctamente  
✔️ Error controlado si no existe

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 10 — Borrar cliente (0,10)

✔️ Cliente eliminado  
✔️ No se puede volver a activar

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 11 — Modificar campo del cliente (0,15)

✔️ Modificado:
- nombre
- dirección  
  ✔️ Confirmación correcta

➡️ **PUNTUACIÓN: 0,15 / 0,15**

---

### ✅ Apartado 12 — Añadir producto al carrito (0,15)

✔️ Añadir múltiples productos  
✔️ Control de ID inexistente  
✔️ Cantidades correctas  
✔️ Producto referenciado desde el XML (requisito clave)

➡️ **PUNTUACIÓN: 0,15 / 0,15**

---

### ✅ Apartado 13 — Mostrar carrito (0,10)

✔️ Líneas detalladas  
✔️ Total correcto  
✔️ Carrito vacío tras pagar

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 14 — Mostrar pedidos (0,10)

✔️ Antes de pagar: sin pedidos  
✔️ Después de pagar: pedido completo con líneas

➡️ **PUNTUACIÓN: 0,10 / 0,10**

---

### ✅ Apartado 15 — Pagar carrito / crear pedido (0,25)

✔️ Cancelación probada  
✔️ Confirmación probada  
✔️ Carrito vaciado  
✔️ Pedido persistido correctamente

➡️ **PUNTUACIÓN: 0,25 / 0,25**

---

### ✅ Apartado 16 — Consulta agregada: total carrito por cliente (0,20)

✔️ Orden ascendente correcto  
✔️ Clientes con total 0 incluidos

➡️ **PUNTUACIÓN: 0,20 / 0,20**

---

### ✅ Apartado 17 — Consulta agregada: total gastado por cliente (0,20)

✔️ Totales correctos  
✔️ Acumulación de múltiples pedidos

➡️ **PUNTUACIÓN: 0,20 / 0,20**

---

## 🏆 RESULTADO FINAL

| Bloque | Puntos |
|------|--------|
| XML (1–7) | 0,55 / 0,55 |
| MongoDB (8–17) | 1,45 / 1,45 |
| **TOTAL** | **🎯 2,00 / 2,00** |

---

## 6️⃣ Conclusión personal

✔️ Todo el enunciado está cubierto  
✔️ Todas las opciones del menú probadas  
✔️ Consultas realizadas directamente con XQuery y MongoDB  
✔️ Errores controlados  
✔️ Proyecto estable y completo

**Estado del proyecto:** ✅ FINALIZADO Y VERIFICADO
