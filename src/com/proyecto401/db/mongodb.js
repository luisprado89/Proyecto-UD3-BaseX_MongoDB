// =========================
// ÚNICA COLECCIÓN: clientes
// - info cliente
// - carrito (ARRAY de items)
// - pedidos (ARRAY de pedidos)
// =========================

// CAMBIAR DE BD CORRECTAMENTE
db = db.getSiblingDB("tienda");

// Limpieza
db.clientes.drop();

// Índice único
db.clientes.createIndex({ email: 1 }, { unique: true });

// Inserción
db.clientes.insertMany([
  // 1) Cliente con CARRITO lleno + 1 pedido (como ejemplo profe)
  {
    nombre: "Lucia Perez",
    email: "lucia.perez@example.com",
    direccion: "C/ Urzaiz 50, Vigo",
    carrito: [
      { producto_id: 1,  nombre: "Laptop HP Pavilion",            cantidad: 2, precio_unitario: 799.99 },
      { producto_id: 3,  nombre: "Tablet Lenovo Tab M10",          cantidad: 1, precio_unitario: 199.99 },
      { producto_id: 11, nombre: "Altavoces Bluetooth JBL Flip 5",  cantidad: 1, precio_unitario: 99.99 }
    ],
    pedidos: [
      {
        pedido_id: new ObjectId(),
        productos: [
          { producto_id: 1, nombre: "Laptop HP Pavilion",        cantidad: 1, precio_unitario: 799.99 },
          { producto_id: 6, nombre: "Cámara DSLR Canon EOS 80D",  cantidad: 1, precio_unitario: 1099.99 }
        ],
        total: 1899.98,
        fecha_pedido: new Date("2026-01-15T12:30:00.000Z")
      }
    ]
  },

  // 2) Cliente con CARRITO lleno (sin pedidos)
  {
    nombre: "Mario Gonzalez",
    email: "mario.gonzalez@example.com",
    direccion: "Av. Gran Via 120, Vigo",
    carrito: [
      { producto_id: 2,  nombre: "Smartphone Samsung Galaxy S21",            cantidad: 1, precio_unitario: 899.99 },
      { producto_id: 4,  nombre: "Auriculares Inalámbricos Sony WH-1000XM4", cantidad: 1, precio_unitario: 299.99 },
      { producto_id: 21, nombre: "Router Wi-Fi 6 TP-Link Archer AX50",       cantidad: 1, precio_unitario: 129.99 }
    ],
    pedidos: []
  },

  // 3) Cliente sin carrito pero con 1 pedido
  {
    nombre: "Ana Rodriguez",
    email: "ana.rodriguez@example.com",
    direccion: "C/ Camelias 22, Vigo",
    carrito: [],
    pedidos: [
      {
        pedido_id: new ObjectId(),
        productos: [
          { producto_id: 31, nombre: "Robot de Cocina Thermomix TM6", cantidad: 1, precio_unitario: 1399.99 }
        ],
        total: 1399.99,
        fecha_pedido: new Date("2026-01-28T09:05:00.000Z")
      }
    ]
  },

  // 4) Cliente con carrito pequeño + 1 pedido (componentes)
  {
    nombre: "David Martinez",
    email: "david.martinez@example.com",
    direccion: "C/ Principe 10, Vigo",
    carrito: [
      { producto_id: 8,  nombre: "Impresora 3D Creality Ender 3",         cantidad: 1, precio_unitario: 249.99 },
      { producto_id: 13, nombre: "Teclado Mecánico RGB Logitech G Pro X", cantidad: 1, precio_unitario: 149.99 }
    ],
    pedidos: [
      {
        pedido_id: new ObjectId(),
        productos: [
          { producto_id: 25, nombre: "Tarjeta Gráfica NVIDIA GeForce RTX 4060", cantidad: 1, precio_unitario: 339.99 },
          { producto_id: 24, nombre: "Memoria RAM Corsair Vengeance 16GB",       cantidad: 2, precio_unitario: 59.99 }
        ],
        total: 459.97,
        fecha_pedido: new Date("2026-01-30T20:10:00.000Z")
      }
    ]
  },

  // 5) Cliente con carrito vacío y 1 pedido grande (Movilidad/Deportes)
  {
    nombre: "Sofia Lopez",
    email: "sofia.lopez@example.com",
    direccion: "C/ Travesia de Vigo 200, Vigo",
    carrito: [],
    pedidos: [
      {
        pedido_id: new ObjectId(),
        productos: [
          { producto_id: 39, nombre: "Patinete Eléctrico Xiaomi Mi Electric Scooter", cantidad: 1, precio_unitario: 449.99 },
          { producto_id: 49, nombre: "Bicicleta Urbana B'Twin Riverside",             cantidad: 1, precio_unitario: 349.99 },
          { producto_id: 50, nombre: "Cinta de Correr ProForm Carbon T7",             cantidad: 1, precio_unitario: 799.99 }
        ],
        total: 1599.97,
        fecha_pedido: new Date("2026-02-01T08:20:00.000Z")
      }
    ]
  }
]);

print("✅ Seed ejecutado correctamente en BD tienda (carrito como array, pedidos mezclados).");
