<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="es_AR"></fmt:setLocale>

    <section id="clientes">
        <div class="container">
            <div class="row">
                <div class="col-md-9">
                    <div class="card">
                        <div class="card-header">
                            <h4>Listado de Clientes</h4>
                        </div>
                        <table class="table table-striped">
                            <thead class="thead-dark">
                                <tr>
                                    <th>#</th>
                                    <th>Nombre</th>
                                    <th>Saldo</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <!<!-- recorrido de la lista de clientes -->
                            <c:forEach var="cliente" items="${clientes}">
                                <tr>
                                    <td>${cliente.idCliente}</td>
                                    <td>${cliente.nombre} ${cliente.apellido}</td>
                                    <td><fmt:formatNumber value="${cliente.saldo}" type="currency"></fmt:formatNumber></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/ServletController?accion=editar&idCliente=${cliente.idCliente}"
                                           class="btn-secondary">
                                            <i class=" fas fa-angle-double-right">Editar</i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!<!-- espacios laterales -->
            <div class="col-md-3">
                <div class="card text-center bg-danger text-white mb-3">
                    <div class="card-body">
                        <h3>Saldo Total</h3>
                        <h4 class="display-4">
                            <fmt:formatNumber value="${saldoTotal}" type="currency"></fmt:formatNumber>
                            </h4>
                        </div>
                    </div>

                    <div class="card text-center bg_succes text-white mb-3">
                        <div>
                            <h3>Total Clientes</h3>
                            <h4 class="display-5">
                                <i class="fas fa-users">${totalClientes}</i>
                            </h4>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>
