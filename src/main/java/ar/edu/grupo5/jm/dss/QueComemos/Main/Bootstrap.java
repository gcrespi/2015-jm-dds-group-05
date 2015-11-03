package ar.edu.grupo5.jm.dss.QueComemos.Main;

import java.time.LocalDate;
import java.util.Arrays;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.RepoUsuarios;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

	public static void main(String[] args) {
		new Bootstrap().run();
	}
	
	public void run() {
		Receta receta = createRecetaExample(); 
		Usuario usuario = createUsuarioExample();
	    withTransaction(() -> {
			new RepoUsuarios(Arrays.asList(usuario));
	    }); 
	    
	    withTransaction(()-> {
			Recetario.instancia.crearReceta(receta, usuario);
			usuario.agregarAFavorita(receta);
	    });

	}

	public Usuario createUsuarioExample(){
		Ingrediente maracuya = new Ingrediente("Maracuya");
		Ingrediente miel = new Ingrediente("Queso");
		Ingrediente queso = new Ingrediente("Miel");
		Ingrediente cebolla = new Ingrediente("Cebolla");
		
		
		Usuario lean = new UsuarioBuilder()
			.setNombre("Leandro")
			.setSexo(Sexo.MASCULINO)
			.setFechaDeNacimiento(LocalDate.parse("1993-11-09"))
			.setPeso(82)
			.setEstatura(1.80)
			.agregarDisgustoAlimenticio(maracuya)
			.agregarDisgustoAlimenticio(miel)
			.agregarPreferenciaAlimenticia(queso)
			.agregarPreferenciaAlimenticia(cebolla)
			.construirUsuario();
		  
		 return lean;
	 }
	
	
	private Receta createRecetaExample() {
		
		Ingrediente papa = new Ingrediente("Papa");
		Ingrediente leche = new Ingrediente("Leche");
		Ingrediente harina = new Ingrediente("Harina");
		Ingrediente carnePicada = new Ingrediente("Carne Picada");
		Ingrediente salsaDeTomate = new Ingrediente("Salsa de Tomate");
		
		Condimentacion sal = new Condimentacion("sal",200);
		Condimentacion pimienta = new Condimentacion("Pimienta",20);
		Condimentacion ajoMolido = new Condimentacion("Ajo Molido",15);
		Condimentacion azucar = new Condimentacion("Azucar",150);
	   
	    String preparacionGnoqui = "Lorem ipsum dolor sit amet, duo cu animal eripuit moderatius";
		String preparacion = "Lorem ipsum dolor sit amet, duo cu animal eripuit moderatius";
		
		Receta gnoquis = new RecetaBuilder()
		.setCantCalorias(150)
		.setNombre("Ñoquis")
		.agregarIngrediente(papa)
		.agregarIngrediente(leche)
		.agregarIngrediente(harina) 
		.setDificultad(Dificultad.MEDIA)
		.agregarCondimentaciones(sal)
		.agregarCondimentaciones(pimienta)
		.agregarCondimentaciones(ajoMolido)
		.setPreparacion(preparacionGnoqui)
		.construirReceta();
	
		Receta receta = new RecetaBuilder()
		.setCantCalorias(150)
		.setNombre("Ñoquis a la boloñesa")
		.agregarIngrediente(carnePicada)
		.agregarIngrediente(salsaDeTomate)
		.setDificultad(Dificultad.MEDIA)
		.agregarCondimentaciones(sal)
		.agregarCondimentaciones(azucar)
		.agregarCondimentaciones(pimienta)
		.agregarCondimentaciones(ajoMolido)
		.agregarSubReceta(gnoquis)
		.setTemporada(Temporada.TODOELAÑO)
		.setPreparacion(preparacion)
		.construirReceta();
		return receta;
	}
  
}
