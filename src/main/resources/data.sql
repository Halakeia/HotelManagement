
INSERT INTO tb_cliente (nome, email, login, senha, celular) VALUES
                                                             ('Ana Silva', 'ana.silva@email.com', 'ana123', 'senhaAna', '11999999999'),
                                                             ('Bruno Souza', 'bruno.souza@email.com', 'bruno123', 'senhaBruno', '21988888888'),
                                                             ('Carla Mendes', 'carla.mendes@email.com', 'carla123', 'senhaCarla', '31977777777'),
                                                             ('Diego Lima', 'diego.lima@email.com', 'diego123', 'senhaDiego', '41966666666'),
                                                             ('Eduarda Reis', 'eduarda.reis@email.com', 'edu123', 'senhaEdu', '51955555555');

INSERT INTO tb_usuario (first_name, last_name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_usuario (first_name, last_name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (1, 1);
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (2, 1);
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (2, 2);


INSERT INTO tb_quarto (descricao, preco, image_url) VALUES
                                                     ('Quarto solteiro com vista para o jardim', 150.00, 'https://img.com/quarto1.jpg'),
                                                     ('Quarto casal com varanda', 250.00, 'https://img.com/quarto2.jpg'),
                                                     ('Suíte luxo com banheira', 400.00, 'https://img.com/quarto3.jpg'),
                                                     ('Quarto duplo econômico', 120.00, 'https://img.com/quarto4.jpg'),
                                                     ('Suíte presidencial', 800.00, 'https://img.com/quarto5.jpg');

INSERT INTO tb_estadia (cliente_id, quarto_id, data_entrada, data_saida) VALUES
                                                                          (1, 1, '2025-05-01', '2025-05-05'),
                                                                          (2, 2, '2025-05-03', '2025-05-06'),
                                                                          (3, 3, '2025-05-02', '2025-05-07'),
                                                                          (4, 4, '2025-05-04', '2025-05-08'),
                                                                          (5, 5, '2025-05-01', '2025-05-09');
