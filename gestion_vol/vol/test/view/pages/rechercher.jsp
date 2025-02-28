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
    <%
    String message = (String) request.getAttribute("message");
    if (message != null) {
  %>
        <!-- Si message existe, on l'affiche dans un paragraphe et on le colorie en vert -->
        <p style="color: green;"><%= message %></p>
  <%
    }
  %>
  <!--  Body Wrapper -->
  <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
    data-sidebar-position="fixed" data-header-position="fixed">
    <!-- Sidebar Start -->
    <aside class="left-sidebar">
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
                  <form action="resultat" method="post">
                    <div class="mb-3">
                      <label for="exampleInputEmail1" class="form-label">Date et heure de depart : </label>
                      <input type="datetime-local" class="form-select" name="date1">
                      <div id="emailHelp" class="form-text">Heure de depart</div>
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputEmail1" class="form-label">Date et heure d'arrivee : </label>
                        <input type="datetime-local" class="form-select" name="date2">
                        <div id="emailHelp" class="form-text">Heure de d'arrivee</div>
                     </div>
                     <% 
                     List<Avion> avions = (List<Avion>) request.getAttribute("avions"); 
                 
                     // Vérification si la liste d'avions n'est pas nulle ou vide
                     if (avions != null && !avions.isEmpty()) { 
                   %>
                     <div class="mb-3">
                         <label for="avionSelect" class="form-label">Choisir un avion</label>
                         <select id="avionSelect" class="form-select" name="avion">
                             <% for (Avion avion : avions) { %>
                                 <option value="<%= avion.getId() %>"><%= avion.getNom() %></option>
                             <% } %>
                         </select>
                     </div>
                 <% } %>

                 <% 
                 List<Ville> villes = (List<Ville>) request.getAttribute("ville"); 
             
                 // Vérification si la liste d'avions n'est pas nulle ou vide
                 if (villes != null && !villes.isEmpty()) { 
               %>
                 <div class="mb-3">
                     <label for="avionSelect" class="form-label">Ville de depart</label>
                     <select id="avionSelect" class="form-select" name="depart">
                         <% for (Ville v : villes) { %>
                             <option value="<%= v.getId() %>"><%= v.getNom() %></option>
                         <% } %>
                     </select>
                 </div>
            
                  
                 <div class="mb-3">
                  <label for="arriveeSelect" class="form-label">Ville d'arrivée :</label>
                  <select id="arriveeSelect" class="form-select" name="arrivee">
                      <% for (Ville ville : villes) { %>
                          <option value="<%= ville.getId() %>"><%= ville.getNom() %> - <%= ville.getPays() %></option>
                      <% } %>
                  </select>
              </div>
          <% } %>

             <button type="submit" class="btn btn-primary">Rechercher</button>
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