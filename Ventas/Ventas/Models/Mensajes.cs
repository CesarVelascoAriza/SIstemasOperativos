using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Ventas.Models
{
    public class Mensajes
    {
        [Key]
        public long ID_Mensajes { set; get; }
        public long ID_Usuario { get; set; }
        public String mensaje { get; set; }

    }
}
