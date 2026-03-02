# 📦 Proyecto 401 — BaseX XML + MongoDB (UD3 Acceso a Datos)

Este repositorio contiene mi proyecto **401** de Acceso a Datos: una plataforma de comercio electrónico con:

- **BaseX (XML)** → productos (datos estáticos)
- **MongoDB** → tienda (clientes, carritos y pedidos)

> ✅ Nota: Este README es una **portada** para GitHub.  
> La documentación completa está en la carpeta **/docs**.

---

## 📚 Documentación del proyecto (en /docs)

### 1) 📄 Enunciado completo + requisitos
➡️ **Enunciado del proyecto (Markdown)**  
[docs/Enunciado_AccesoAdatos(AD)ProyectoMongoDB_BaseX.md](docs/Enunciado_AccesoAdatos(AD)ProyectoMongoDB_BaseX.md)

*(Aquí está el texto íntegro del enunciado y condiciones de entrega.)*

---

### 2) ✅ Checklist FINAL (pruebas reales + puntuación 2/2)
➡️ **Checklist de verificación personal**  
[docs/README_ChecklistFINAL.md](docs/README_ChecklistFINAL.md)

*(Aquí está la verificación apartado por apartado con pruebas reales ejecutadas.)*

---

## 📦 Estructura del repositorio

- `src/` → código Java de la aplicación (menú + servicios)
- `libs/` → dependencias `.jar` usadas en IntelliJ (BaseX y MongoDB driver)
- `docs/` → documentación (enunciado + checklist)
- `src/com/proyecto401/db/basex.xml` → XML de productos (BaseX)
- `src/com/proyecto401/db/mongodb.js` → script para MongoDB (tienda)

---

## ▶️ Ejecución rápida (idea general)

1. Inicia **BaseX Server** (puerto 1984) y carga la BD `productos` con el XML.
2. Inicia **MongoDB** en local (puerto 27017) y ejecuta `mongodb.js`.
3. Ejecuta `ApplicationMenu` desde IntelliJ.

---

## 🧾 Entrega (según enunciado)

La entrega se genera como **401.zip** e incluye:

- `mongodb.js`
- `basex.xml`
- `src/`

📌 El repositorio contiene además `docs/` y `libs/` para tenerlo todo ordenado y ejecutable al clonar.
# 🏆 Evaluación Oficial — Proyecto 401 (BaseX + MongoDB)

📅 Evaluado el: **25 de febrero de 2026**  
👨‍🏫 Profesor evaluador:   
📊 Calificación final: **20,00 / 20,00**

---

## 📊 Desglose según rúbrica oficial

| Nº | Apartado | Resultado | Puntuación obtenida |
|----|----------|-----------|---------------------|
| 1  | Modificar elemento por ID | ✅ BEN | **1 / 1** |
| 2  | Eliminar producto | ✅ BEN | **1 / 1** |
| 3  | Consulta 1 (XML) | ✅ BEN | **0.5 / 0.5** |
| 4  | Consulta 2 (XML) | ✅ BEN | **0.5 / 0.5** |
| 5  | Consulta 3 (XML) | ✅ BEN | **0.5 / 0.5** |
| 6  | Consulta 4 (XML) | ✅ BEN | **1 / 1** |
| 7  | Consulta 5 (XML) | ✅ BEN | **1 / 1** |
| 8  | Crear cliente | ✅ BEN | **1 / 1** |
| 9  | Identificar usuario por email | ✅ BEN | **1 / 1** |
| 10 | Borrar cliente | ✅ BEN | **1 / 1** |
| 11 | Modificar valor de un campo | ✅ BEN | **1.5 / 1.5** |
| 12 | Añadir producto al carrito | ✅ BEN | **1.5 / 1.5** |
| 13 | Mostrar el carrito | ✅ BEN | **1 / 1** |
| 14 | Mostrar pedidos del cliente | ✅ BEN | **1 / 1** |
| 15 | Pagar el carrito | ✅ BEN | **2.5 / 2.5** |
| 16 | Consulta agregada 1 (MongoDB) | ✅ BEN | **2 / 2** |
| 17 | Consulta agregada 2 (MongoDB) | ✅ BEN | **2 / 2** |
| 📊 Calificación final:| | | **20,00 / 20,00**|

**20,00 / 20,00**
---

## 🏁 Resultado final

> 🔥 **Proyecto calificado con la puntuación máxima en todos los apartados.**  
> ✔ Todos los criterios evaluados como **BEN** (máxima valoración).  
> ✔ Implementación completa de BaseX (XQuery) y MongoDB (CRUD + agregaciones).

---

## 📌 Observación

El proyecto cumple íntegramente los requisitos del enunciado oficial y supera todos los criterios de evaluación establecidos en la rúbrica académica.