using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Ventas.Models;


namespace Ventas.Controllers.Api
{
    [Produces("application/json")]
    [Route("api/UsuarioChat")]
    public class UsuarioChatController : Controller
    {
        private DatosContex datosComtex;

        public UsuarioChatController(DatosContex datosContex)
        {
            this.datosComtex = datosContex;
        }
        [HttpGet]
        public IActionResult Get()
        {
            return Ok(this.datosComtex.Mensaje.ToList());
        }
        [HttpPost]
        public IActionResult AgregarMensaje([FromBody]Mensajes mensaje)
        {
                this.datosComtex.Mensaje.Add(mensaje);
                this.datosComtex.SaveChanges();
                return Created("Get", mensaje);
        }

    }
}