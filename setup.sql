-- Database and users are now created by run.sh using .env variables
-- This file contains only the schema and initial data

CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  telefone VARCHAR(30),
  email VARCHAR(120) NOT NULL,
  senha_hash VARCHAR(255) NOT NULL,
  endereco VARCHAR(255),
  is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS itens_cardapio (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  descricao TEXT,
  preco DECIMAL(10,2) NOT NULL,
  ativo BOOLEAN DEFAULT TRUE
);

INSERT INTO usuarios (nome, telefone, email, senha_hash, endereco, is_admin)
SELECT 'Admin', '0000000000', 'admin@local', '123', 'Endereco padrao', TRUE
WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'admin@local'
);

INSERT INTO itens_cardapio (nome, descricao, preco, ativo)
SELECT 'Pizza Margherita', 'Molho de tomate, mussarela e manjericao', 35.90, TRUE
WHERE NOT EXISTS (
  SELECT 1 FROM itens_cardapio WHERE nome = 'Pizza Margherita'
);

INSERT INTO itens_cardapio (nome, descricao, preco, ativo)
SELECT 'Hamburguer Artesanal', 'Pao brioche, carne 180g, queijo e molho', 28.50, TRUE
WHERE NOT EXISTS (
  SELECT 1 FROM itens_cardapio WHERE nome = 'Hamburguer Artesanal'
);

INSERT INTO itens_cardapio (nome, descricao, preco, ativo)
SELECT 'Suco Natural', 'Suco natural de laranja 300ml', 8.00, TRUE
WHERE NOT EXISTS (
  SELECT 1 FROM itens_cardapio WHERE nome = 'Suco Natural'
);

CREATE TABLE IF NOT EXISTS pedidos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
  total_preco DECIMAL(10,2) NOT NULL,
  endereco VARCHAR(255) NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS itens_pedidos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  pedido_id INT NOT NULL,
  item_cardapio_id INT NOT NULL,
  quantidade INT NOT NULL,
  preco_unitario DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
  FOREIGN KEY (item_cardapio_id) REFERENCES itens_cardapio(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS carrinhos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  item_cardapio_id INT NOT NULL,
  quantidade INT NOT NULL,
  UNIQUE KEY unique_user_item (usuario_id, item_cardapio_id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
  FOREIGN KEY (item_cardapio_id) REFERENCES itens_cardapio(id) ON DELETE CASCADE
);
