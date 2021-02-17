
package Modelo;

import javax.swing.*;
public class IO 
{
    public static String leerPalabra(String mensaje)
    {
        String palabra = "";
        palabra = JOptionPane.showInputDialog(mensaje);
        return palabra;
    }
    public static int leerEntero(String mensaje)
    {
        int entero = 0;
        entero = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
        return entero;
    }
    public static double leerDecimal(String mensaje)
    {
        double decimal = 0.0;
        decimal = Double.parseDouble(JOptionPane.showInputDialog(mensaje));
        return decimal;
    }
    public static char leerCaracter(String mensaje)
    {
        char caracter = ' ';
        caracter = JOptionPane.showInputDialog(mensaje).toUpperCase().charAt(0);
        return caracter;
    }
    public static void mostrar(String mensaje)
    {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
