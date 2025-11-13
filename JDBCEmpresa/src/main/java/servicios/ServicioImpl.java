package servicios;

import dao.CrudDAO;
import dao.DepartamentoDAO;
import dao.EmpleadoDAO;
import datasource.Database;
import modelo.Departamento;
import modelo.Empleado;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServicioImpl implements Servicio{
    @Override
    public void altaDepartamento(Departamento departamento) throws SQLException {
        try(Connection con = Database.getConexion()) {
            CrudDAO<Departamento> dao = new DepartamentoDAO(con);
            dao.insertar(departamento);
        }
    }

    @Override
    public void altaEmpleado(Empleado empleado) throws SQLException {
        try(Connection con = Database.getConexion()) {
            CrudDAO<Empleado> dao = new EmpleadoDAO(con);
            dao.insertar(empleado);
        }
    }

    @Override
    public List<Departamento> listarDepartamentos() throws SQLException {
        try(Connection con = Database.getConexion()) {
            CrudDAO<Departamento> dao = new DepartamentoDAO(con);
            return dao.listar();
        }
    }

    @Override
    public List<Empleado> listarEmpleados() throws SQLException {
        try(Connection con = Database.getConexion()) {
            CrudDAO<Empleado> dao = new EmpleadoDAO(con);
            return dao.listar();
        }
    }

    @Override
    public Departamento obtenerDepartamento(long id) throws SQLException {
        try(Connection con = Database.getConexion()) {
            CrudDAO<Departamento> dao = new DepartamentoDAO(con);
            return dao.obtener(id);
        }
    }

    @Override
    public Empleado obtenerEmpleado(long id) throws SQLException {
        try(Connection con = Database.getConexion()) {
            CrudDAO<Empleado> dao = new EmpleadoDAO(con);
            return dao.obtener(id);
        }
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) throws SQLException {
        try( Connection con = Database.getConexion()) {
            CrudDAO<Empleado> dao = new EmpleadoDAO(con);
            dao.actualizar(empleado);
        }
    }

    @Override
    public void bajaEmpleado(Empleado empleado) throws SQLException {
        try( Connection con = Database.getConexion()) {
            CrudDAO<Empleado> dao = new EmpleadoDAO(con);
            dao.eliminar(empleado);
        }
    }

    @Override
    public Map<String, Double> saldoMedioDepartamento() throws SQLException {
        List<Empleado> empleados = listarEmpleados();
        Map<String, Double> mediaSaldo = empleados.stream()
                .collect(Collectors.groupingBy(c -> c.getIdDepartamento().getNombre(), Collectors.averagingDouble(Empleado::getSueldo)));
        return mediaSaldo;
    }
}
