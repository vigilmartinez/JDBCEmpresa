package servicios;

import modelo.Departamento;
import modelo.Empleado;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface Servicio {
    void altaDepartamento(Departamento departamento ) throws SQLException;
    void altaEmpleado( Empleado empleado ) throws SQLException;
    List<Departamento> listarDepartamentos() throws SQLException;
    List<Empleado> listarEmpleados() throws SQLException;
    Departamento obtenerDepartamento( long id ) throws SQLException;
    Empleado obtenerEmpleado( long id ) throws SQLException;
    void actualizarEmpleado( Empleado empleado ) throws SQLException;
    void bajaEmpleado( Empleado empleado ) throws SQLException;
    Map<String, Double> saldoMedioDepartamento() throws SQLException;
}
