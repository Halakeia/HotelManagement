
INSERT INTO cliente (nome, email, login, senha, celular) VALUES
                                                             ('Ana Silva', 'ana.silva@email.com', 'ana123', 'senhaAna', '11999999999'),
                                                             ('Bruno Souza', 'bruno.souza@email.com', 'bruno123', 'senhaBruno', '21988888888'),
                                                             ('Carla Mendes', 'carla.mendes@email.com', 'carla123', 'senhaCarla', '31977777777'),
                                                             ('Diego Lima', 'diego.lima@email.com', 'diego123', 'senhaDiego', '41966666666'),
                                                             ('Eduarda Reis', 'eduarda.reis@email.com', 'edu123', 'senhaEdu', '51955555555');

INSERT INTO usuario (nome, email, senha, is_admin) VALUES
                                                       ('Admin Master', 'admin@email.com', 'admin123', true),
                                                       ('João Pedro', 'joao.pedro@email.com', 'senhaJoao', false),
                                                       ('Mariana Costa', 'mariana.costa@email.com', 'senhaMari', false),
                                                       ('Fernando Rocha', 'fernando.rocha@email.com', 'senhaFern', false),
                                                       ('Lívia Gomes', 'livia.gomes@email.com', 'senhaLivia', false);


INSERT INTO quarto (descricao, preco, image_url) VALUES
                                                     ('Quarto solteiro com vista para o jardim', 150.00, 'https://img.com/quarto1.jpg'),
                                                     ('Quarto casal com varanda', 250.00, 'https://img.com/quarto2.jpg'),
                                                     ('Suíte luxo com banheira', 400.00, 'https://img.com/quarto3.jpg'),
                                                     ('Quarto duplo econômico', 120.00, 'https://img.com/quarto4.jpg'),
                                                     ('Suíte presidencial', 800.00, 'https://img.com/quarto5.jpg');

INSERT INTO estadia (cliente_id, quarto_id, data_entrada, data_saida) VALUES
                                                                          (1, 1, '2025-05-01', '2025-05-05'),
                                                                          (2, 2, '2025-05-03', '2025-05-06'),
                                                                          (3, 3, '2025-05-02', '2025-05-07'),
                                                                          (4, 4, '2025-05-04', '2025-05-08'),
                                                                          (5, 5, '2025-05-01', '2025-05-09');
