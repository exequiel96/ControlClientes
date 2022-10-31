package web;

import datos.ClienteDaoJDBC;
import domain.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletController")
public class ServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "editar":
                    this.editarCliente(request, response);
                    break;
                case "eliminar":
                    this.eliminarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = new ClienteDaoJDBC().listar();
        System.out.println("clientes = " + clientes);
        HttpSession sesion = request.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", calcularSaldoTotal(clientes));
        response.sendRedirect("clientes.jsp");
    }

    //saldo total
    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;
        for (Cliente cliente : clientes) {
            saldoTotal += cliente.getSaldo();
        }
        return saldoTotal;

    }
    
    private void editarCliente (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        //primero recupero el idCliente
        int idCliente= Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente= new ClienteDaoJDBC().encontrar(new Cliente(idCliente));
        request.setAttribute("ciente", cliente);
        //pagina jsp que se va a usar
        String jspEditar= "/WEB-INF/paginas/cliente/editarCliente.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
        
    }

    //metodo doPost para agregar un cliente con formulario
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    this.modificarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //primero recupero los valores del formulario
        double saldo = 0;

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //creacion del objeto cliente(modelo MVC)
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);

        //lo inserto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().insertar(cliente);
        System.out.println("registrosModificados = " + registrosModificados);

        //la info del cliente nuevo ya esta guardad en la bd
        //vuelta a la accion de default
        this.accionDefault(request, response);

    }
    
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //aca recupero la info que viene desde el fomrulario editarCliente
        int idCliente= Integer.parseInt(request.getParameter("idCliente"));
        
        double saldo = 0;

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //creacion del objeto cliente(modelo MVC)
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

        //modifico en la base de datos con el metodo actualizar()
        int registrosModificados = new ClienteDaoJDBC().actualizar(cliente);
        System.out.println("registrosModificados = " + registrosModificados);

        //la info del cliente nuevo ya esta guardad en la bd
        //vuelta a la accion de default
        this.accionDefault(request, response);

    }
    
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //aca recupero la info que viene desde el fomrulario editarCliente
        int idCliente= Integer.parseInt(request.getParameter("idCliente"));
        
       

        //creacion del objeto cliente(modelo MVC)
        Cliente cliente = new Cliente(idCliente);

        //elimino en la base de datos con el metodo eliminar()
        int registrosModificados = new ClienteDaoJDBC().eliminar(cliente);
        System.out.println("registrosModificados = " + registrosModificados);

        //la info del cliente nuevo ya esta guardad en la bd
        //vuelta a la accion de default
        this.accionDefault(request, response);

    }
}

