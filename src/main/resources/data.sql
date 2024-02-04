-- data.sql

-- Insert Employers
INSERT INTO servicecenter.employers (code, name, number, email, password) VALUES
                                                                ('E001', '',0, 'admin', 'admin');


-- Insert Access
INSERT INTO servicecenter.access (id, storeAccess, inventoryAccess, customerAccess, reportAccess, repairAccess, employer_id) VALUES
                                                                                                                   ('A001', true, true, true, true, true, 'E001');  -- Make sure 'EMP001' exists in Employers
                                                                                                                   -- Make sure 'EMP002' exists in Employers
