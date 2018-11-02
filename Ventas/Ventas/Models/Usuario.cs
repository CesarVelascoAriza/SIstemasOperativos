using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Ventas.Models
{
    public class Usuario
    {
        [Key]
        public long ID_Usuario{ get; set;  }
        public string nombre { get; set; }
        public string apellido { get; set; }
    }
}
