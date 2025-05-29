
INSERT INTO tb_usuario (name, email, celular, password) VALUES
                                                             ('Ana Silva', 'ana.silva@email.com', '11999999999', 'senhaAna'),
                                                             ('Bruno Souza', 'bruno.souza@email.com', '21988888888', 'senhaBruno'),
                                                             ('Carla Mendes', 'carla.mendes@email.com', '31977777777', 'senhaCarla'),
                                                             ('Diego Lima', 'diego.lima@email.com', '41966666666', 'senhaDiego'),
                                                             ('Eduarda Reis', 'eduarda.reis@email.com', '51955555555', 'senhaEdu');

INSERT INTO tb_role (authority) VALUES ('ADMIN');
INSERT INTO tb_role (authority) VALUES ('AUTENTICADO');
INSERT INTO tb_role (authority) VALUES ('NAO_AUTENTICADO');
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES (1, 1),
                                                        (2, 1),
                                                        (2, 2),
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
