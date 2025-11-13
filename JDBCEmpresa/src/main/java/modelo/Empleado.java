package modelo;

public class Empleado {

    private long id;
    private String dni;
    private String nombre;
    private double sueldo;
    private Departamento idDepartamento;

    public Empleado() {

    }

    public Empleado(String dni, String nombre, double sueldo, Departamento idDepartamento) {
        this.dni = dni;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.idDepartamento = idDepartamento;
    }

    public Empleado(long id, String dni, String nombre, double sueldo, Departamento idDepartamento) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.idDepartamento = idDepartamento;
    }

    public long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSueldo() {
        return sueldo;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", sueldo=" + sueldo +
                ", idDepartamento=" + idDepartamento +
                '}';
    }
}
