import helpers.ConsoleHelper;
import modelo.Departamento;
import modelo.Empleado;
import servicios.Servicio;
import servicios.ServicioImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    public static final int ALTA_DEPARTAMENTO = 1;
    public static final int ALTA_EMPLEADO = 2;
    public static final int LISTAR_EMPLEADOS = 3;
    public static final int TRANSFERENCIA_EMPLEADOS = 4;
    public static final int BAJA_EMPLEADOS = 5;
    public static final int SALDO_MEDIO = 6;
    public static final int SALIR = 0;

    public static int menu() {
        System.out.println("1.- ALTA DEPARTAMENTO");
        System.out.println("2.- ALTA EMPLEADO");
        System.out.println("3.- LISTAR EMPLEADO");
        System.out.println("4.- TRANSFERENCIA EMPLEADO");
        System.out.println("5.- BAJA EMPLEADO");
        System.out.println("6.- MEDIA SALDO POR EMPLEADO");
        System.out.println("0.- SALIR");
        return ConsoleHelper.pedirEntero("Opcion", 0, 6);
    }

    public static Departamento obtenerDepartamento() throws SQLException {
        Servicio servicio = new ServicioImpl();
        Departamento departamentoSeleccionado = null;
        List<Departamento> departamentos = servicio.listarDepartamentos();
        departamentos.forEach(System.out::println);
        do {
            long id = ConsoleHelper.pedirEntero("Indica ID de departamento: ");
            departamentoSeleccionado = departamentos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
            if (departamentoSeleccionado == null) System.out.println("Departamento ID incorrecto.");
        } while (departamentoSeleccionado == null);
        return departamentoSeleccionado;
    }

    public static Empleado obtenerEmpleado() throws SQLException {
        Servicio servicio = new ServicioImpl();
        Empleado empleadoSeleccionado = null;
        List<Empleado> empleados = servicio.listarEmpleados();
        empleados.forEach(System.out::println);
        do {
            long id = ConsoleHelper.pedirEntero("Indica ID de empleado: ");
            empleadoSeleccionado = empleados.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
            if (empleadoSeleccionado == null) System.out.println("Empleado ID incorrecto.");
        } while (empleadoSeleccionado == null);
        return empleadoSeleccionado;
    }

    public static void altaDepartamento() {
        try {
            Servicio servicio = new ServicioImpl();

            String nombre = ConsoleHelper.pedirCadena("Nombre del departamento: ");
            Departamento nuevoDepartamento = new Departamento(nombre);

            servicio.altaDepartamento(nuevoDepartamento);

            System.out.println(nuevoDepartamento);
            System.out.println(nuevoDepartamento.getNombre() + " dado de alta");
        } catch (SQLException ex) {
            System.out.println("Error dando de alta departamento: " + ex.getMessage());
        }
    }


    public static void altaEmpleado() {
        try {
            Servicio servicio = new ServicioImpl();
            String dni = ConsoleHelper.pedirCadena("DNI del empleado: ");
            String nombre = ConsoleHelper.pedirCadena("Nombre del empleado: ");
            double sueldo = ConsoleHelper.pedirDecimal("Sueldo del empleado: ");
            Departamento departamentoSeleccionado = obtenerDepartamento();
            Empleado nuevoEmpleado = new Empleado(dni, nombre, sueldo, departamentoSeleccionado);

            servicio.altaEmpleado(nuevoEmpleado);

            System.out.println(nuevoEmpleado);
            System.out.println(nuevoEmpleado.getNombre() + " dado de alta");
        } catch (SQLException ex) {
            System.out.println("Error dando de alta empleado: " + ex.getMessage());
        }
    }

    public static void listarEmpleadosDepartamento() {
        try {
            Servicio servicio = new ServicioImpl();
            Departamento departamentoSeleccionado = obtenerDepartamento();
            List<Empleado> empleadosPorDepartamento = servicio.listarEmpleados()
                    .stream()
                    .filter(e -> e.getIdDepartamento().getId() == departamentoSeleccionado.getId())
                    .toList();
            System.out.println("Empleados del departamento: " + departamentoSeleccionado.getNombre());
            empleadosPorDepartamento.forEach(System.out::println);
        } catch (SQLException ex) {
            System.out.println("Error listando empleados por departamento: " + ex.getMessage());
        }
    }

    public static void transferenciaEmpleado() {
        try {
            Servicio servicio = new ServicioImpl();
            Empleado empleadoTransferido = obtenerEmpleado();
            Departamento departamentoNuevo = obtenerDepartamento();
            empleadoTransferido.setIdDepartamento(departamentoNuevo);
            servicio.actualizarEmpleado(empleadoTransferido);

            System.out.println(empleadoTransferido.getNombre() + " transferido correctamente a " + departamentoNuevo.getNombre());
        } catch (SQLException ex) {
            System.out.println("Error transfiriendo empleado: " + ex.getMessage());
        }
    }

    public static void bajaEmpleado() {
        try {
            Servicio servicio = new ServicioImpl();
            Empleado empleadoSeleccionado = obtenerEmpleado();
            servicio.bajaEmpleado(empleadoSeleccionado);

            System.out.println(empleadoSeleccionado.getNombre() + " dado de baja");
        } catch (SQLException ex) {
            System.out.println("Error dando de baja empleado: " + ex.getMessage());
        }
    }

    public static void salarioMedio() {
        try {
            Servicio servicio = new ServicioImpl();
            Map<String, Double> totales = servicio.saldoMedioDepartamento();
            System.out.println("SALDO MEDIO POR DEPARTAMENTO: ");
            totales.forEach((nombre_departamento, recuento) -> {
                System.out.printf("%30s - %.2f€\n", nombre_departamento, recuento);
            });
        } catch (SQLException ex) {
            System.out.println("Error calculando salario medio: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        int opcion;
        do {
            opcion = menu();
            if (opcion == ALTA_DEPARTAMENTO)
                altaDepartamento();
            else if (opcion == ALTA_EMPLEADO)
                altaEmpleado();
            else if (opcion == LISTAR_EMPLEADOS)
                listarEmpleadosDepartamento();
            else if (opcion == TRANSFERENCIA_EMPLEADOS)
                transferenciaEmpleado();
            else if (opcion == BAJA_EMPLEADOS)
                bajaEmpleado();
            else if (opcion == SALDO_MEDIO)
                salarioMedio();
        } while (opcion != SALIR);
        System.out.println("Fin de programa.");
    }
}
