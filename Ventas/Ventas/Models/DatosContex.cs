using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;

namespace Ventas.Models
{
    public class DatosContex : DbContext
    {
        
        public DatosContex(DbContextOptions<DatosContex> options) : base(options)
        {
        }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Mensajes> Mensaje { get; set; }
    }
}
