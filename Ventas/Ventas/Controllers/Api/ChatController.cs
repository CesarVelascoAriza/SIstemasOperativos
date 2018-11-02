using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Ventas.Models;

namespace Ventas.Controllers.Api
{
    [Produces("application/json")]
    [Route("api/Chat")]
    public class ChatController : Controller
    {
        private DatosContex datosComtex;

        public ChatController(DatosContex datosContex)
        {
            this.datosComtex = datosContex;
        }
        [HttpGet]
        public IActionResult Get()
        {
            return Ok(this.datosComtex.Usuarios.ToList());
        }
        [HttpPost]
        public IActionResult Post([FromBody]Usuario usuario)
        {
            this.datosComtex.Usuarios.Add(usuario);
            this.datosComtex.SaveChanges();
            return Created("Get",usuario);
        }


    }
}