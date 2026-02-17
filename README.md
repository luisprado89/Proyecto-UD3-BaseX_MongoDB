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
