package Modelo;

import java.sql.*; //Librería para realizar querys hacia la Base de Datos.
import java.util.ArrayList;

public class BaseDeDatos {

    public int cant;
    public int m;
    private Driver Oracle;
    private Connection conexion;
    private boolean cedulaRegistrada = false;
    private int cod_lugar;
    private String cargo;

    public String getCargo() {
        return cargo;
    }

    public BaseDeDatos() {
        //Cargar la base de datos:
        try {

            //Se carga el driver JDBC.
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            //nombre del servidor
            String nombre_servidor = "localhost";
            //numero del puerto
            String numero_puerto = "1521";
            //SID
            String sid = "xe";
            //URL "jdbc:oracle:thin:@nombreServidor:numeroPuerto:SID"
            String url = "jdbc:oracle:thin:@" + nombre_servidor + ":" + numero_puerto + ":" + sid;

            //Nombre usuario y password
            String usuario = "SYSTEM";
            String password = "123";

            //Obtiene la conexion
            conexion = DriverManager.getConnection(url, usuario, password);

        } catch (Exception ex) {
            //Imprime error de la consulta.
            ex.printStackTrace();
            IO.mostrar("ERROR FATAL, No existe una conexion con la base de datos: \n" + ex.toString());
            System.exit(0);
        }
    }

    public boolean getCedulaRegistrada() {
        return cedulaRegistrada;
    }

    public int getCod_Lugar() {
        return cod_lugar;
    }

    //Ingresar registros.
    public void insertarRegistro(int lugar, int horaLlamada, boolean llamado, int turno, String motivoConsulta, String tipoUsuario) {
        try {
            //Variable para el codigo sql
            String codSql;

            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            //Guardamos la consulta a realizar en una variable.
            codSql = "insert into REGISTRO values (seq_cod_registro.Nextval," + lugar + ", to_date(SYSDATE, 'dd/mm/yy'), "
                    + horaLlamada + ", '" + llamado + "', " + turno + ", '" + motivoConsulta + "', '" + tipoUsuario + "')";

            //La mostramos en consola y la ejecutamos.
            System.out.println(codSql);
            consulta.executeUpdate(codSql);
        } catch (Exception ex) {
            //DO SOMETHING.
        }
    }

    //Ingresar registros de titulares.
    public void insertarRegistroTitular(int codCedula) {
        try {
            //Variable para el codigo sql
            String codSql;

            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            //Guardamos la consulta a realizar en una variable.
            codSql = "insert into REGISTRO_TITULAR values (seq_cod_registro_titular.Nextval, seq_cod_registro.Currval, " + codCedula + ")";

            //La mostramos en consola y la ejecutamos.
            System.out.println(codSql);
            consulta.executeUpdate(codSql);
        } catch (Exception ex) {
            //DO SOMETHING.
        }
    }

    //actualizar llamado de un registro.
    public void actualizarRegistro(boolean llamado, int codRegistro, int cod_lugar) {
        try {
            //Variable para el codigo sql
            String codSql;

            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            //Guardamos la consulta a realizar en una variable.
            codSql = "update REGISTRO set llamado = '" + llamado + "', COD_LUGAR = " + cod_lugar + " where cod_registro = " + codRegistro;

            //La mostramos en consola y la ejecutamos.
            System.out.println(codSql);
            consulta.executeUpdate(codSql);
        } catch (Exception ex) {
            //DO SOMETHING.
        }
    }

    //Consultar si la cedula esta registrada
    public int consultarCedulas(String cedula) {
        try {
            int cod_cedula = 0;
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            //Guardamos la consulta a realizar en una variable.
            ResultSet codSql = consulta.executeQuery("select COD_TITULAR from TITULAR where IDENTIFICACION = '" + cedula + "'");

            while (codSql.next()) {
                cod_cedula = codSql.getInt("COD_TITULAR");
            }
            System.out.println(cod_cedula);
            if (cod_cedula == 0) {
                cedulaRegistrada = false;
            } else {
                cedulaRegistrada = true;
            }
            return cod_cedula;
        } catch (Exception ex) {
            //DO SOMETHING.
            System.out.println("Mensaje: " + ex.getMessage());
            return 0;
        }
    }

    public String obtenerCedula(String cod_registro) {
        String cedula = "";
        try {
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            String sql = "select IDENTIFICACION from TITULAR, REGISTRO_TITULAR, REGISTRO "
                    + "where registro.cod_registro = registro_titular.cod_registro and "
                    + "registro_titular.cod_titular = titular.cod_titular and "
                    + "registro.cod_registro = '" + cod_registro + "'";

            //Guardamos la consulta a realizar en una variable.
            ResultSet codSql = consulta.executeQuery(sql);

            while (codSql.next()) {
                cedula = codSql.getString("IDENTIFICACION");
            }
            System.out.println(cedula);
            return cedula;
        } catch (Exception ex) {
            //DO SOMETHING.
            System.out.println("Mensaje: " + ex.getMessage());
            return cedula;
        }
    }

    public String consultarCorreo(String cedula) {
        String correo = "";
        try {
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            //Guardamos la consulta a realizar en una variable.
            ResultSet codSql = consulta.executeQuery("select CORREO from TITULAR where IDENTIFICACION = '" + cedula + "'");

            while (codSql.next()) {
                correo = codSql.getString("CORREO");
            }
            System.out.println(correo);
            return correo;
        } catch (Exception ex) {
            //DO SOMETHING.
            System.out.println("Mensaje: " + ex.getMessage());
            return correo;
        }
    }

    //Consultar Login empleados.
    public boolean consultarLoginEmpleados(String username, String password) {
        try {
            int cod_login_empleado = 0;
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            //Guardamos la consulta a realizar en una variable.
            ResultSet codSql = consulta.executeQuery("select * from LOGIN_EMPLEADO where USERNAME = '" + username + "' and PASSWORD = '" + password + "'");

            while (codSql.next()) {
                cod_login_empleado = codSql.getInt("COD_LOGIN_EMPLEADO");
                cargo = codSql.getString("CARGO").toUpperCase();
            }
            if (cod_login_empleado != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //DO SOMETHING.
            System.out.println("Mensaje: " + ex.getMessage());
            return false;
        }
    }

    //Mostrar algun todos los registros sin llamar.
    public String[][] consultarRegistrosSinLlamar(String tipoLugar, int numero) {
        try {
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();
            String SQL;
            String SQLCant;

            //Consulta sacar cod_lugar.
            ResultSet lugar = consulta.executeQuery("SELECT COD_LUGAR FROM LUGAR WHERE TIPO_LUGAR = '" + tipoLugar + "' AND NUMERO_LUGAR = " + numero);
            while (lugar.next()) {
                cod_lugar = lugar.getInt("COD_LUGAR");
            }

            if (tipoLugar.equals("ASESOR")) {
                SQL = "SELECT * FROM REGISTRO where registro.llamado = 'false' and fecha_registro = to_date(SYSDATE, 'dd/mm/yy') and motivo_consulta like '%Consulta Asesor%'";
                SQLCant = "SELECT count(*) FROM REGISTRO where registro.llamado = 'false' and fecha_registro = to_date(SYSDATE, 'dd/mm/yy') and motivo_consulta like '%Consulta Asesor%'";
            } else {
                SQL = "SELECT * FROM REGISTRO where registro.llamado = 'false' and fecha_registro = to_date(SYSDATE, 'dd/mm/yy') and motivo_consulta not like '%Consulta Asesor%'";
                SQLCant = "SELECT count(*) FROM REGISTRO where registro.llamado = 'false' and fecha_registro = to_date(SYSDATE, 'dd/mm/yy') and motivo_consulta not like '%Consulta Asesor%'";
            }
            //Hacemos una consulta para guardar la cantidad de filas en la tabla registro.
            ResultSet cantidadFilas = consulta.executeQuery(SQLCant);
            //guardamos el dato obtenido de la base de datos
            while (cantidadFilas.next()) {
                cant = cantidadFilas.getInt("count(*)");
            }
            System.out.println("Cantidad: " + cant); //Debug de la cantidad.

            //Creamos un vector de dos dimensiones con la cantidad de filas y columnas ya establecidas
            String[][] consultaArray = new String[cant][6];
            int num = 0;
            //Obtiene el resultado de la busqueda de todos las filas de la base de datos.
            ResultSet resultado = consulta.executeQuery(SQL);
            System.out.println(SQL);
            //En cada registro seleccionado asignamos sus valores dentro del vector consultaArray-
            while (resultado.next()) {

                consultaArray[num][0] = resultado.getString("cod_registro");
                consultaArray[num][1] = resultado.getString("cod_lugar");
                consultaArray[num][2] = resultado.getString("hora_llegada");
                consultaArray[num][3] = resultado.getString("turno");
                consultaArray[num][4] = resultado.getString("motivo_consulta");
                consultaArray[num][5] = resultado.getString("tipo_usuario");
                num++;//Sumar para empezar por la siguiente fila
            }
            System.out.println("El numero llego hasta: " + num); //Debug del numero.
            num = 0; //Asignamos el numero de nuevo a 0.

            return consultaArray;
        } catch (Exception ex) {
            //DO SOMETHING
            return null; //No retornamos ningun valor.
        }
    }

    //Mostrar los registros de un día especifico:
    public ArrayList<Turno> consultarRegistrosPorDia(String fecha, ArrayList<Turno> array, String tipoUsu, boolean cja, boolean assor) {
        try {
            String[] lugar = new String[2];
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();
            String SQL = "";
            if (tipoUsu.equals("EA") || tipoUsu.equals("EP")) {
                SQL = "SELECT tipo_usuario, turno, motivo_consulta, cod_lugar, to_char(fecha_registro, 'dd/mm/yy') \"Fecha\", to_char(hora_llegada, '99,99') \"hora\", TITULAR.* FROM REGISTRO, REGISTRO_TITULAR, TITULAR where registro.fecha_registro = '"
                        + fecha + "' and registro.tipo_usuario = '" + tipoUsu + "' and registro.cod_registro = registro_titular.cod_registro and registro_titular.cod_titular = Titular.cod_titular";
            } else {
                SQL = "SELECT tipo_usuario, turno, motivo_consulta, cod_lugar, to_char(fecha_registro, 'dd/mm/yy') \"fecha\" , to_char(hora_llegada, '99,99') \"hora\" FROM REGISTRO where registro.fecha_registro = '"
                        + fecha + "' and registro.tipo_usuario = '" + tipoUsu + "'";
            }
            System.out.println("SQL: " + SQL);

            //Creamos un vector de dos dimensiones con la cantidad de filas y columnas ya establecidas
            ArrayList<Turno> consultaArray = array;
            //Obtiene el resultado de la busqueda de todos las filas de la base de datos.
            ResultSet resultado = consulta.executeQuery(SQL);

            //En cada registro seleccionado asignamos sus valores dentro del vector consultaArray-
            while (resultado.next()) {
                String turno, motivo, modulo, fechaTurno, hora, id, correo;
                turno = resultado.getString("tipo_usuario") + "-" + resultado.getString("turno");
                motivo = resultado.getString("motivo_consulta");
                modulo = resultado.getString("cod_lugar");
                lugar = consultarLugar(modulo);
                if(!lugar[0].equals("WAITING")){
                    modulo = lugar[0] + " " + lugar[1];
                }else{
                    modulo = "No asignado";
                }
                fechaTurno = resultado.getString("fecha");
                hora = resultado.getString("hora");
                if (tipoUsu.equals("EA") || tipoUsu.equals("EP")) {
                    id = resultado.getString("identificacion");
                    correo = resultado.getString("correo");
                } else {
                    id = "";
                    correo = "";
                }
                hora = hora.replace(",", ":");
                if (cja && assor) {
                    consultaArray.add(new Turno(turno, modulo, hora, fechaTurno, motivo, id, correo));
                } else if (cja && !assor) {
                    if (lugar[0].equals("CAJA")) {
                        consultaArray.add(new Turno(turno, modulo, hora, fechaTurno, motivo, id, correo));
                    }
                } else if (!cja && assor) {
                    if (lugar[0].equals("ASESOR")) {
                        consultaArray.add(new Turno(turno, modulo, hora, fechaTurno, motivo, id, correo));
                    }
                }
                else{
                    if (lugar[0].equals("WAITING")) {
                        consultaArray.add(new Turno(turno, modulo, hora, fechaTurno, motivo, id, correo));
                    }
                }
            }
            return consultaArray;
        } catch (Exception ex) {
            //DO SOMETHING
            System.out.println(ex.toString());
            return null; //No retornamos ningun valor.
        }
    }

    //Consultar los lugares.
    public String[] consultarLugar(String cod_lugar) {
        try {
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            String[] resu = new String[2];

            //Hacemos una consulta para guardar la cantidad de filas en la tabla registro.
            ResultSet cantidadFilas = consulta.executeQuery("SELECT * FROM LUGAR where cod_lugar = " + cod_lugar);
            //guardamos el dato obtenido de la base de datos
            while (cantidadFilas.next()) {
                resu[0] = cantidadFilas.getString("tipo_lugar");
                resu[1] = cantidadFilas.getString("numero_lugar");
            }
            return resu;
        } catch (Exception e) {
            return null;
        }

    }

    public int registroT(String nombre, String apellidos, String correo, String id, String fecha_nac, String numero, String pin) {
        try {
            String codSql, codSql2;
            //Crea un objeto SQLServerStatement que genera objetos.
            Statement consulta = conexion.createStatement();

            codSql2 = "SELECT COD_TITULAR FROM TITULAR WHERE IDENTIFICACION = '" + id + "' OR CORREO = '" + correo + "'";
            System.out.println(codSql2);
            ResultSet cantidadFilas = consulta.executeQuery(codSql2);
            //guardamos el dato obtenido de la base de datos
            if (cantidadFilas.next()) {
                return 1;
            } else {
                //Guardamos la consulta a realizar en una variable.
                codSql = "insert into TITULAR values(Seq_cod_titular.Nextval, '" + nombre + "', '" + apellidos + "', '" + correo + "', '" + id + "', '" + fecha_nac + "', '" + numero + "', '" + pin + "')";

                //La mostramos en consola y la ejecutamos.
                System.out.println(codSql);
                consulta.executeUpdate(codSql);
                return 0;
            }

        } catch (SQLException e) {
            IO.mostrar(e.getMessage());
            return 1;
        }
    }
}
