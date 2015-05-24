package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;

public class Usuario {
	private Complexion complexion;
	private DatosPersonales datosPersonales;
	private Collection<String> preferenciasAlimenticias;
	private Collection<String> disgustosAlimenticios;
	private Collection<CondicionDeSalud> condicionesDeSalud;
	private Collection<Grupo> grupos;

	public enum Rutina {
		LEVE, NADA, MEDIANA, INTENSIVA, ALTA
	}

	private Rutina rutina;
	private Collection<Receta> recetasPropias;
	private Collection<Receta> recetasFavoritas;

	public Collection<Receta> getRecetasFavoritas() {
		return recetasFavoritas;
	}

	private static Collection<Receta> recetasPublicas;

	public Usuario(DatosPersonales unosDatosPersonales,
			Complexion unaComplexion,
			Collection<String> unasPreferenciasAlimenticias,
			Collection<String> unosDisgustosAlimenticios,
			Collection<Receta> unasRecetasPropias,
			Collection<CondicionDeSalud> unasCondicionesDeSalud,
			Rutina unaRutina) {

		datosPersonales = unosDatosPersonales;
		complexion = unaComplexion;
		grupos = new ArrayList<Grupo>();
		recetasFavoritas = new ArrayList<Receta>();

		preferenciasAlimenticias = (unasPreferenciasAlimenticias != null) ? unasPreferenciasAlimenticias
				: new ArrayList<String>();
		disgustosAlimenticios = (unosDisgustosAlimenticios != null) ? unosDisgustosAlimenticios
				: new ArrayList<String>();
		recetasPropias = (unasRecetasPropias != null) ? unasRecetasPropias
				: new ArrayList<Receta>();
		condicionesDeSalud = (unasCondicionesDeSalud != null) ? unasCondicionesDeSalud
				: new ArrayList<CondicionDeSalud>();

		rutina = unaRutina;
	}

	public void agregarGrupo(Grupo unGrupo) {
		grupos.add(unGrupo);
	}

	// Setter de variable de clase recetasPublicas
	public static void setRecetasPublicas(Collection<Receta> recetas) {
		recetasPublicas = recetas;
	}

	public double getPeso() {
		return complexion.getPeso();
	}

	public boolean indicaSexo() {
		return datosPersonales.indicaSexo();
	}

	public Collection<Receta> getRecetasPropias() {
		return recetasPropias;
	}

	// Punto 1
	// XXX esto podria no ser facil de extender
	public boolean esUsuarioValido() {
		return datosPersonales.sonValidos() && complexion.esComplexionValida()
				&& rutina != null && esUsuarioValidoParaSusCondiciones();
	}

	public boolean esUsuarioValidoParaSusCondiciones() {
		return condicionesDeSalud.stream().allMatch(
				condicion -> condicion.esUsuarioValido(this));
	}

	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
	}

	// Punto 2.b
	public boolean sigueRutinaSaludable() {
		return 18 <= complexion.indiceMasaCorporal()
				&& complexion.indiceMasaCorporal() <= 30
				&& this.subsanaTodasLasCondiciones();
	}

	private boolean subsanaTodasLasCondiciones() {
		return condicionesDeSalud.stream().allMatch(
				condicion -> condicion.subsanaCondicion(this));
	}

	public boolean tieneRutinaActiva() {
		return this.tieneRutinaIntensiva() || rutina == Rutina.ALTA;
	}

	public boolean tieneRutinaIntensiva() {
		return rutina == Rutina.INTENSIVA;
	}

	public boolean tienePreferencia(String preferencia) {
		return preferenciasAlimenticias.contains(preferencia);
	}

	public boolean tieneAlgunaDeEstasPreferencias(
			Collection<String> preferencias) {
		return preferenciasAlimenticias.stream().anyMatch(
				preferencia -> preferencias.contains(preferencia));
	}

	// Punto 3.a
	public void crearReceta(Receta unaReceta) {
		if (!unaReceta.esValida()) {
			throw new RecetaNoValidaException(
					"No se Puede agregar una receta no válida!!!",
					new RuntimeException());
		}
		recetasPropias.add(unaReceta);
	}

	// Punto 3.b en receta
	public boolean sosRecetaInadecuadaParaMi(Receta unaReceta) {
		return condicionesDeSalud.stream().anyMatch(
				condicion -> condicion.esInadecuada(unaReceta));
	}// si no tiene condiciones devuelve false por lo tanto no es inadecuada

	// Punto 4.a y 4.b
	public boolean puedeAcceder(Receta unaReceta) {
		return esRecetaPropia(unaReceta) || esRecetaPublica(unaReceta)
				|| esRecetaDeGrupo(unaReceta);
	}

	public boolean esRecetaDeGrupo(Receta unaReceta) {
		return grupos.stream().anyMatch(
				unGrupo -> unGrupo.alguienTiene(unaReceta));
	}

	public boolean esRecetaPropia(Receta unaReceta) {
		return recetasPropias.contains(unaReceta);
	}

	private boolean esRecetaPublica(Receta unaReceta) {
		return recetasPublicas.contains(unaReceta);
	}

	// Punto 4.c
	public void modificarReceta(Receta viejaReceta, Receta nuevaReceta) {
		if (!puedeAcceder(viejaReceta)) {
			throw new NoPuedeAccederARecetaException(
					"No tiene permiso para acceder a esa receta",
					new RuntimeException());
		}

		if (esRecetaPropia(viejaReceta)) {
			modificarRecetaPropia(viejaReceta, nuevaReceta);
		} else {
			crearReceta(nuevaReceta);
		}
	}

	private void modificarRecetaPropia(Receta viejaReceta, Receta nuevaReceta) {
		eliminarRecetaPropia(viejaReceta);
		crearReceta(nuevaReceta);
	}

	public void eliminarRecetaPropia(Receta unaReceta) {
		if (recetasFavoritas.contains(unaReceta))
			recetasFavoritas.remove(unaReceta);
		recetasPropias.remove(unaReceta);
	}

	// Punto 5
	public void crearRecetaConSubRecetas(Receta unaReceta,
			Collection<Receta> unasSubRecetas) {
		if (unasSubRecetas.stream().allMatch(
				unaSubReceta -> puedeAcceder(unaSubReceta))) {
			unaReceta.agregarSubRecetas(unasSubRecetas);
			crearReceta(unaReceta);
		} else {
			throw new NoPuedeAccederARecetaException(
					"No puede agregar subrecetas a las que no tenga permiso de acceder",
					new RuntimeException());
		}

	}

	public boolean puedeSugerirse(Receta unaReceta) {
		return noLeDisgusta(unaReceta) && !sosRecetaInadecuadaParaMi(unaReceta);
	}

	public Collection<Receta> consultarRecetasDeLosGrupos() {
		Collection<Receta> resultadoConsulta = new ArrayList<Receta>();
		grupos.forEach(unGrupo -> resultadoConsulta.addAll(unGrupo
				.consultarRecetas()));

		return resultadoConsulta;
	}

	public Collection<Receta> consultarRecetas(IFiltro unFiltro) {
		Collection<Receta> resultadoConsulta = new ArrayList<Receta>();
		resultadoConsulta.addAll(recetasPropias);
		resultadoConsulta.addAll(consultarRecetasDeLosGrupos());
		resultadoConsulta.addAll(recetasPublicas);

		return unFiltro.filtrarRecetas(resultadoConsulta, this);
	}

	public Collection<Receta> consultarRecetasSt(Filtrado unFiltrado) {
		Collection<Receta> resultadoConsulta = new ArrayList<Receta>();
		resultadoConsulta.addAll(recetasPropias);
		resultadoConsulta.addAll(consultarRecetasDeLosGrupos());
		resultadoConsulta.addAll(recetasPublicas);

		return unFiltrado.aplicarFiltros(resultadoConsulta, this);
	}

	public boolean noLeDisgusta(Receta unaReceta) {
		return !unaReceta.tieneAlgunIngredienteDeEstos(disgustosAlimenticios);
	}

	public void agregarAFavorita(Receta unaReceta) {
		recetasFavoritas.add(unaReceta);
	}
}
