Primeros Avances 
1. Realizar las configuración de carpetas basicas de la estructura de Sanchez
2. Realizar las entidades basicas con las funcionalidades de Sprinboot (Mirarse el PDF del profe y realizar la practica)
3. Realizar el DASHBOARD PUBLICO en HTML y CSS, este puede usar bootstrap para su diseño
4. Realizar los controladores basicos para el public
5. Ir pensando en las demas paginas (Login.html - Dashboard [Admin-Oferente-Empresa])

Cosas basicas No hay base de datos (o por lo menos creada por nosotros), ya que sprinboot realiza esta misma como lo explico el profe
Ya hay un model que proporciona el sprinboot entonces nos enfocaremos en los Services y Controller con sus respectivos tipos @ que da sprinboot
Evitemos el uso de IA ya que la estructura de sanchez es muy delicada entonces solo nos limitamos a borradores (HTML CSS o cuando no sepamos algo)


ESTRUCTURA DEL PROYECTO

├── data/
│   ├── UsuarioRepository.java
│   ├── EmpresaRepository.java
│   ├── OferenteRepository.java
│   ├── PuestoRepository.java
│   ├── CaracteristicaRepository.java
│   ├── PuestoCaracteristicaRepository.java
│   └── OferenteHabilidadRepository.java
├── logic/
│   ├── Usuario.java        
│   ├── Empresa.java
│   ├── Oferente.java
│   ├── Administrador.java
│   ├── Puesto.java
│   ├── Caracteristica.java
│   ├── PuestoCaracteristica.java
│   ├── OferenteHabilidad.java
│   └── Service.java
