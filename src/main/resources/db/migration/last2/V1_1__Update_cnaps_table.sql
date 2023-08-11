CREATE OR REPLACE FUNCTION validate_employees_exists()
    RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM test1.employee WHERE id = NEW.id) THEN
        RAISE EXCEPTION 'Author with ID % does not exist in test1.authors', NEW.id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER validate_employee_trigger
    BEFORE INSERT ON test2.cnaps
    FOR EACH ROW
EXECUTE FUNCTION validate_employees_exists();