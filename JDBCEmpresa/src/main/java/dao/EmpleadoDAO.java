package dao;

import modelo.Departamento;
import modelo.Empleado;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO implements CrudDAO<Empleado>{

    private Connection con;

    public EmpleadoDAO( Connection con ) {
        this.con = con;
    }

    @Override
    public void insertar(Empleado obj) throws SQLException {
        String sql = "INSERT INTO empleados( id, dni, nombre, sueldo, id_departamento ) VALUES ( ?, ?, ?, ?, ? )";
        try(PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setLong(1, obj.getId());
            pst.setString(2, obj.getDni());
            pst.setString(3, obj.getNombre());
            pst.setDouble(4, obj.getSueldo());
            pst.setLong(5, obj.getIdDepartamento().getId());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if ( rs.next()) {
                obj.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public List<Empleado> listar() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        CrudDAO<Departamento> departamentoDAO = new DepartamentoDAO(con);
        String sql = "SELECT id, dni, nombre, sueldo, id_departamento FROM empleados";
        try( Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while( rs.next()) {
                long id = rs.getLong("id");
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                double sueldo = rs.getDouble("sueldo");
                long idDepartamento = rs.getLong("id_departamento");
                Departamento departamento = departamentoDAO.obtener(idDepartamento);
                empleados.add(new Empleado(id, dni, nombre, sueldo, departamento));
            }
        }
        return empleados;
    }

    @Override
    public void eliminar(Empleado obj) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try( PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, obj.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void actualizar(Empleado obj) throws SQLException {
        String sql = "UPDATE empleados SET dni = ?, nombre = ?, sueldo = ?, id_departamento = ? WHERE id = ?";
        try( PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, obj.getDni());
            pst.setString(2, obj.getNombre());
            pst.setDouble(3, obj.getSueldo());
            pst.setLong(4, obj.getIdDepartamento().getId());
            pst.setLong(5, obj.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public Empleado obtener(long id) throws SQLException {
        Empleado objEmpleado = null;
        CrudDAO<Departamento> departamentoDAO = new DepartamentoDAO(con);
        String sql = "SELECT id, dni, nombre, sueldo, id_departamento FROM empleados WHERE id = ?";
        try( PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if ( rs.next() ) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                double sueldo = rs.getDouble("sueldo");
                long idDepartamento = rs.getLong("id_departamento");
                Departamento departamento = departamentoDAO.obtener(idDepartamento);
                objEmpleado = new Empleado(id, dni, nombre, sueldo, departamento);
            }
        }
        return objEmpleado;
    }
}
