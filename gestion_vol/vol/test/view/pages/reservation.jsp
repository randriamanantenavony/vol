<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*, src.models.*" %>

<!doctype html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>MaterailM Free Bootstrap Admin Template by WrapPixel</title>
  <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/assets/images/logos/favicon.png" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.min.css" />
</head>

<body>
  <!--  Body Wrapper -->
  <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
    data-sidebar-position="fixed" data-header-position="fixed">
    <!-- Sidebar Start -->
    <aside class="left-sidebar">3
      <!-- Sidebar scroll-->
      <div>
        <div class="brand-logo d-flex align-items-center justify-content-between">
          <a href="./index.html" class="text-nowrap logo-img">
            <img src="${pageContext.request.contextPath}/assets/images/logos/logo.svg" alt="" />
          </a>
          <div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
            <i class="ti ti-x fs-8"></i>
          </div>
        </div>
        <!-- Sidebar navigation-->
        <%@ include file="sidebar.jsp" %>
        <!-- End Sidebar navigation -->
      </div>
      <!-- End Sidebar scroll-->
    </aside>
    <!--  Sidebar End -->
    <!--  Main wrapper -->
    <div class="body-wrapper">
      <!--  Header Start -->
      <%@ include file="header.jsp" %>
      <!--  Header End -->
      <div class="body-wrapper-inner">
        <div class="container-fluid">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title fw-semibold mb-4">Forms</h5>
              <div class="card">
                <div class="card-body">
                  <form action="save_reservation" method="post" enctype="multipart/form-data">
                    <div class="mb-3">


                      <% 
                      List<Vol> vols = (List<Vol>) request.getAttribute("vols"); 
                  
                      // Vérification si la liste d'avions n'est pas nulle ou vide
                      if (vols != null && !vols.isEmpty()) { 
                    %>
                      <div class="mb-3">
                          <label for="avionSelect" class="form-label">Choisir un vol</label>
                          <select id="avionSelect" class="form-select" name="vol">
                              <% for (Vol vol : vols) { %>
                                  <option value="<%= vol.getId() %>">VOL0000<%= vol.getId() %></option>
                              <% } %>
                          </select>
                      </div>
                  <% } %>

                 <% 
                 List<Typesiege> typeSieges = (List<Typesiege>) request.getAttribute("types"); 
             
                 // Vérification si la liste d'avions n'est pas nulle ou vide
                 if (typeSieges != null && !typeSieges.isEmpty()) { 
               %>
                 <div class="mb-3">
                     <label for="avionSelect" class="form-label">Type de siege : </label>
                     <select id="avionSelect" class="form-select" name="typesiege">
                         <% for (Typesiege t : typeSieges) { %>
                             <option value="<%= t.getId() %>"><%= t.getLibelle() %></option>
                         <% } %>
                     </select>
                 </div>
          <% } %>
         
          <%
           Passager passager = (Passager) request.getSession().getAttribute("passager");
          %>
          <input type="hidden" name="passager" value="<%= passager.getId() %>">
            <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Nombre de siege : </label>
            <input type="number" class="form-select" name="siege">
            <div id="emailHelp" class="form-text">Nombre de siege</div>
         </div>

         <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">image de votre passeport : </label>
            <input type="file" class="form-select" name="passeport">
            <div id="emailHelp" class="form-text">image de votre passeport</div>
         </div>

             <button type="submit" class="btn btn-primary">Reserver</button>
            </form>
                </div>
              </div>
             
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="${pageContext.request.contextPath}/assets/libs/jquery/dist/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/sidebarmenu.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/app.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/libs/simplebar/dist/simplebar.js"></script>
  <!-- solar icons -->
  <script src="https://cdn.jsdelivr.net/npm/iconify-icon@1.0.8/dist/iconify-icon.min.js"></script>
</body>

</html>