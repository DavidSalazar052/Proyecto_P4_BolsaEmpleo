-- ─────────────────────────────────────────────────────────────────────────────
-- data.sql
-- Inserta el administrador inicial solo si no existe ya en la BD.
-- Usa INSERT INTO ... SELECT para no depender de un ID fijo,
-- lo que evita conflictos con el AUTO_INCREMENT de Hibernate.
-- ─────────────────────────────────────────────────────────────────────────────

-- 1. Inserta el usuario admin solo si no existe ese username
INSERT INTO usuario (username, clave, tipo)
SELECT 'admin', 'admin123', 'ADM'
WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE username = 'admin'
);

-- 2. Inserta el administrador vinculado al usuario recién creado (o ya existente)
INSERT INTO administrador (usuario_id, identificacion, nombre, correo)
SELECT id, '000000000', 'Administrador', 'admin@bolsaempleo.local'
FROM usuario
WHERE username = 'admin'
  AND NOT EXISTS (
      SELECT 1 FROM administrador
      WHERE usuario_id = (SELECT id FROM usuario WHERE username = 'admin')
  );
