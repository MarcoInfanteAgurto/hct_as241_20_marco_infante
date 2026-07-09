# HACKATHON AS241S5 - Alquiler de Vehículos

Este repositorio contiene la solución completa para el sistema de **Alquiler de Vehículos** desarrollado bajo una arquitectura de microservicios con **Spring WebFlux (Reactivo)**, base de datos **MongoDB Atlas**, un frontend en **Angular 21** (Zoneless) y empaquetado para su despliegue y orquestación con **Docker** y **Kubernetes**.

---

## 🐳 Referencia de Imágenes en Docker Hub

Las imágenes ya están compiladas bajo **Java 26** y publicadas en Docker Hub. Se pueden descargar de forma pública en cualquier máquina ejecutando los siguientes comandos:

```bash
# Descargar Microservicio Vehículos
docker pull marco2909/hct-vehiculo:latest
https://hub.docker.com/r/marco2909/hct-vehiculo

# Descargar Microservicio Clientes
docker pull marco2909/hct-cliente:latest
https://hub.docker.com/r/marco2909/hct-cliente

# Descargar Microservicio Alquileres (Transaccional)
docker pull marco2909/hct-alquiler:latest
https://hub.docker.com/r/marco2909/hct-alquiler

# Descargar Frontend (Angular expuesto en Nginx)
docker pull marco2909/hct-frontend:latest
https://hub.docker.com/r/marco2909/hct-frontend

```

---

## 🚀 Guía de Despliegue en Kubernetes

Sigue las siguientes instrucciones paso a paso para levantar y testear todo el sistema en tu clúster de Kubernetes local.

### 1. Prerrequisitos
* Asegúrate de que **Docker Desktop** esté encendido y que **Kubernetes** esté habilitado (puedes activarlo desde la configuración de engranaje en Docker Desktop -> *Kubernetes* -> *Enable Kubernetes*).

### 2. Aplicar Manifiestos de Kubernetes
Abre tu consola de comandos (PowerShell o terminal) parada en la raíz del proyecto y ejecuta el siguiente comando para desplegar todos los recursos (Namespaces, Secrets, Services y Deployments):

```powershell
kubectl apply -f ./k8s/manifest-vehiculo/,./k8s/manifest-cliente/,./k8s/manifest-alquiler/,./k8s/manifest-frontend/
```

### 3. Verificar que los contenedores estén corriendo
Comprueba el estado de inicialización de los Pods en todos los namespaces del clúster ejecutando:

```powershell
kubectl get pods --all-namespaces
```
*Espera hasta que el estado (`STATUS`) de todos los componentes figure como **`Running`**.*

---

## 🌐 Pruebas y Acceso en la Web

Dado que los navegadores web locales (Chrome, Edge, etc.) no pueden acceder directamente a la red interna de Kubernetes, se debe configurar una redirección de puertos (*Port-Forwarding*) para poder usar la aplicación en `localhost`.

### 1. Activar Reenvío de Puertos (Port-Forward)
Abre 4 consolas o pestañas independientes y ejecuta en cada una de ellas los siguientes comandos para enlazar la web y las APIs a tu máquina local:

```powershell
# Consola 1: Enlace al Frontend (Angular)
kubectl port-forward svc/hct-frontend-service 8080:80 -n hct-frontend

# Consola 2: Enlace a Microservicio de Vehículos
kubectl port-forward svc/hct-vehiculo-service 8081:8081 -n hct-vehiculo

# Consola 3: Enlace a Microservicio de Clientes
kubectl port-forward svc/hct-cliente-service 8082:8082 -n hct-cliente

# Consola 4: Enlace a Microservicio de Alquileres
kubectl port-forward svc/hct-alquiler-service 8083:8083 -n hct-alquiler
```

### 2. Abrir la Aplicación
Una vez activos los comandos de redirección anteriores, ingresa al sistema abriendo tu navegador en la siguiente URL:

👉 [**http://localhost:8080**](http://localhost:8080)

* **Nota Importante de Uso**: Para navegar en la aplicación, haz clic directamente en los botones de navegación superiores del menú (`Vehículos`, `Clientes`, `Alquileres`). Evita utilizar el refresco de pantalla (`F5`) para mantener el enrutamiento interno de Angular de forma fluida.
