
INSERT INTO tb_usuario (name, email, login, celular, password) VALUES
                                                                   ('Ana Silva', 'ana.silva@email.com', 'ana', '11999999999', '$2a$10$cs6HR2PYpb7V2ON8iRk.HuO6E2PxZgRfXsPqvPiZf89ldMNkIC0Yi'),
                                                                   ('Bruno Souza', 'bruno.souza@email.com', 'bruno.souza@email.com', '21988888888', '$2a$10$88azWRplZ3AhlVmC5RM3oOmnlETDL0zn7Fw64cYsdzTBAF/bXdbQq'),
                                                                   ('Carla Mendes', 'carla.mendes@email.com', 'carla.mendes@email.com', '31977777777', '$2a$10$FxrWGu3oMfcY5bGO.vHhA.68nB6NRVaXgG6l/EBeGiLxFZxQOIdfC'),
                                                                   ('Diego Lima', 'diego.lima@email.com', 'diego.lima@email.com', '41966666666', '$2a$10$X0bRcFtBYVDdPLarF01ksuvCPzBPMYIYyYLSJ5bh3q3r4bG4pqZjy'),
                                                                   ('Eduarda Reis', 'eduarda.reis@email.com', 'eduarda.reis@email.com', '51955555555', '$2a$10$XT0nb3D3ESKlM/sxy1VW0uDwrBgmcrzTZU9FwyoRbREhNnrNBhnS6');


INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_NAO_AUTENTICADO');
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (1, 1),
                                                        (2, 1),
                                                        (3, 3),
                                                        (4, 3),
                                                        (5, 3);


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
