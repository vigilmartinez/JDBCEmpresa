package dao;

import modelo.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO implements CrudDAO<Departamento>{

    private Connection con;

    public DepartamentoDAO( Connection con ) {
        this.con = con;
    }

    @Override
    public void insertar(Departamento obj) throws SQLException {
        String sql = "INSERT INTO departamentos( id, nombre ) VALUES ( ?, ? )";
        try(PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setLong(1, obj.getId());
            pst.setString(2, obj.getNombre());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if ( rs.next()) {
                obj.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public List<Departamento> listar() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM departamentos";
        try(Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while( rs.next()) {
                long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                departamentos.add(new Departamento(id, nombre));
            }
        }
        return departamentos;
    }

    @Override
    public void eliminar(Departamento obj) throws SQLException {
        String sql = "DELETE FROM departamentos WHERE id = ?";
        try( PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, obj.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public void actualizar(Departamento obj) throws SQLException {
        String sql = "UPDATE departamentos SET nombre = ? WHERE id = ?";
        try( PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, obj.getNombre());
            pst.setLong(2, obj.getId());
            pst.executeUpdate();
        }
    }

    @Override
    public Departamento obtener(long id) throws SQLException {
        Departamento departamento = null;
        String sql = "SELECT id, nombre FROM departamentos WHERE id = ?";
        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if( rs.next()) {
                String nombre = rs.getString("nombre");
                departamento = new Departamento(id, nombre);
            }
        }
        return departamento;
    }
}
