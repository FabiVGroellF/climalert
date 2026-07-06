# Resumen

Climalert funcionará como un servicio autónomo, sin interfaz gráfica, cuya responsabilidad es conectarse periódicamente a un proveedor meteorológico externo, procesar los datos recibidos y notificar por correo
electrónico a las entidades correspondientes cuando se detecten condiciones climáticas peligrosas o inusuales.

Para el envío de correos se usa Mailtrap.

# Capturas de Pantalla

### APP en funcionamiento
<img width="1600" height="133" alt="image" src="https://github.com/user-attachments/assets/7d8d1e42-72bf-4723-8640-4054548c2d03" />
Se hace una consulta del clima cada 5 minutos.
Se guarda la consulta hecha.
Se consulta la última consulta hecha cada 1 minuto para saber si hay que mandar una alerta crítica.

### Test de Envío de Mail con Alerta Crítica
<img width="1600" height="160" alt="image" src="https://github.com/user-attachments/assets/4f0a5cf2-f4a0-4a5d-a24a-ff2ea3cea0f2" />

Se fuerzan condiciones climáticas peligrosas para enviar la alerta por mail.


<img width="547" height="399" alt="image" src="https://github.com/user-attachments/assets/5d583a20-bb06-4998-adfb-be1b0a338284" />

El mail con la alerta llega exitosamente a la casilla de Mailtrap.
