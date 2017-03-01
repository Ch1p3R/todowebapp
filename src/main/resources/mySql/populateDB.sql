DELETE FROM account;
DELETE FROM todo;
INSERT INTO account(name, password) VALUES
('Baba Misha', '123456'),
('Antonio', 'qwerty'),
('Kira', 'Ks{cvd[sd');

INSERT INTO todo(description, account_id) VALUES
  ('Some description by BabaMiha', 1),
  ('One more by BabaMiha', 1),
  ('Some description by Antonio', 2),
  ('That\'s created by Kira', 3),
  ('Olol la', 3)