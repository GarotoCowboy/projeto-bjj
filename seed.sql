-- ==============================================================================
-- SEED DE DADOS: SISTEMA DE GESTÃO DE ACADEMIA BJJ
-- Compatível com PostgreSQL e H2 Database
-- ==============================================================================

-- ------------------------------------------------------------------------------
-- 1. GRADUAÇÕES (tb_graduations)
-- ------------------------------------------------------------------------------
INSERT INTO tb_graduations (belt, degree, created_at, updated_at) VALUES
('WHITE', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('WHITE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('WHITE', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('WHITE', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('WHITE', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BLUE', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BLUE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BLUE', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BLUE', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BLUE', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PURPLE', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PURPLE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PURPLE', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PURPLE', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PURPLE', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BROWN', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BROWN', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BROWN', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BROWN', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BROWN', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BLACK', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BLACK', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (belt, degree) DO NOTHING;

-- ------------------------------------------------------------------------------
-- 2. ALUNOS (tb_students) - 20 Alunos
-- ------------------------------------------------------------------------------
INSERT INTO tb_students (name, email, phone_number, birthday, is_active, weight, height, is_health_problem, health_problem_description, responsible_id, created_at, updated_at) VALUES
('Carlos Gracie Neto', 'carlos.gracie@email.com', '11981110001', '1998-04-15', true, 78, 177, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Royce Silva', 'royce.silva@email.com', '11981110002', '1995-11-20', true, 80, 182, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Rickson Machado', 'rickson.machado@email.com', '11981110003', '1990-07-12', true, 85, 180, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Marcelo Garcia', 'marcelo.garcia@email.com', '11981110004', '1992-01-25', true, 77, 173, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bernardo Faria', 'bernardo.faria@email.com', '11981110005', '1993-09-08', true, 98, 188, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Rodolfo Vieira', 'rodolfo.vieira@email.com', '11981110006', '1994-06-18', true, 99, 186, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Marcus Buchecha', 'marcus.buchecha@email.com', '11981110007', '1991-03-02', true, 105, 190, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kyra Gracie', 'kyra.gracie@email.com', '11981110008', '1996-05-29', true, 60, 165, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bia Mesquita', 'bia.mesquita@email.com', '11981110009', '1997-08-14', true, 64, 168, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Lucas Lepri', 'lucas.lepri@email.com', '11981110010', '1993-12-05', true, 75, 175, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Caio Terra', 'caio.terra@email.com', '11981110011', '1995-02-17', true, 58, 164, true, 'Asma leve controlada', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Felipe Pena', 'felipe.pena@email.com', '11981110012', '1994-10-30', true, 96, 187, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nicholas Meregali', 'nicholas.meregali@email.com', '11981110013', '1999-07-21', true, 97, 192, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mica Galvão', 'mica.galvao@email.com', '11981110014', '2003-10-08', true, 77, 178, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tainan Dalpra', 'tainan.dalpra@email.com', '11981110015', '2000-11-19', true, 82, 181, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Diego Pato', 'diego.pato@email.com', '11981110016', '1998-09-03', true, 64, 167, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Erich Munis', 'erich.munis@email.com', '11981110017', '2001-04-10', true, 98, 193, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Gabriel Arges', 'gabriel.arges@email.com', '11981110018', '1996-03-15', false, 82, 179, true, 'Cirurgia anterior no joelho', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kaynan Duarte', 'kaynan.duarte@email.com', '11981110019', '1999-01-22', true, 102, 189, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Lucas Pinheiro', 'lucas.pinheiro@email.com', '11981110020', '1997-02-11', true, 64, 166, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- ------------------------------------------------------------------------------
-- 3. MENSALIDADES (tb_memberships) - 24 Registros
-- Status: PAID (Pago), PENDING no futuro (A Pagar), PENDING no passado (Atrasado)
-- ------------------------------------------------------------------------------

-- === MENSALIDADES PAGAS (status = 'PAID', payment_date preenchido) ===
INSERT INTO tb_memberships (student_id, amount, contract_time, status, payment_date, due_date, created_at, updated_at) VALUES
(1, 220.00, 'MONTHLY',     'PAID', '2026-09-01', '2026-09-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 220.00, 'MONTHLY',     'PAID', '2026-09-03', '2026-09-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 600.00, 'TRIMESTER',   'PAID', '2026-08-28', '2026-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1100.00, 'SEMI_ANNUAL', 'PAID', '2026-07-10', '2026-07-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2000.00, 'ANNUAL',     'PAID', '2026-01-05', '2026-01-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 250.00, 'MONTHLY',     'PAID', '2026-09-04', '2026-09-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 250.00, 'MONTHLY',     'PAID', '2026-08-30', '2026-09-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 220.00, 'MONTHLY',     'PAID', '2026-09-02', '2026-09-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- === MENSALIDADES A PAGAR / EM DIA (status = 'PENDING', due_date no futuro) ===
INSERT INTO tb_memberships (student_id, amount, contract_time, status, payment_date, due_date, created_at, updated_at) VALUES
(9,  220.00, 'MONTHLY',     'PENDING', NULL, '2026-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 220.00, 'MONTHLY',     'PENDING', NULL, '2026-09-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 220.00, 'MONTHLY',     'PENDING', NULL, '2026-09-25', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 600.00, 'TRIMESTER',   'PENDING', NULL, '2026-09-30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 250.00, 'MONTHLY',     'PENDING', NULL, '2026-10-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 250.00, 'MONTHLY',     'PENDING', NULL, '2026-10-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1100.00, 'SEMI_ANNUAL', 'PENDING', NULL, '2026-10-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 2000.00, 'ANNUAL',     'PENDING', NULL, '2026-10-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- === MENSALIDADES ATRASADAS (status = 'PENDING', due_date no passado) ===
INSERT INTO tb_memberships (student_id, amount, contract_time, status, payment_date, due_date, created_at, updated_at) VALUES
(17, 220.00, 'MONTHLY',   'PENDING', NULL, '2026-09-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 220.00, 'MONTHLY',   'PENDING', NULL, '2026-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 250.00, 'MONTHLY',   'PENDING', NULL, '2026-08-25', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 250.00, 'MONTHLY',   'PENDING', NULL, '2026-08-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1,  220.00, 'MONTHLY',   'PENDING', NULL, '2026-08-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  220.00, 'MONTHLY',   'PENDING', NULL, '2026-07-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 600.00, 'TRIMESTER', 'PENDING', NULL, '2026-07-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 220.00, 'MONTHLY',   'PENDING', NULL, '2026-06-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

