package br.com.gympass;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Piloto implements Comparable<Piloto> {
	private int codigoPiloto;
	private String Nome;
	private List<DadosVolta> voltas = new ArrayList<DadosVolta>();
	private Calendar tempoTotalProva = null;
	private Double velocidadeMediaPiloto = null;
	private int numeroTotalVoltas = 0;
	private String melhorVolta = null;

	public Piloto(int codigoPiloto, String nome) {
		super();
		this.codigoPiloto = codigoPiloto;
		Nome = nome;
	}

	public int getCodigoPiloto() {
		return codigoPiloto;
	}

	public void setCodigoPiloto(int codigoPiloto) {
		this.codigoPiloto = codigoPiloto;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public List<DadosVolta> getVoltas() {
		return voltas;
	}

	public void setVoltas(List<DadosVolta> voltas) {
		this.voltas = voltas;
	}

	public Calendar getTempoTotalProva() {
		return tempoTotalProva;
	}

	public void setTempoTotalProva(Calendar tempoTotalProva) {
		this.tempoTotalProva = tempoTotalProva;
	}

	public Double getVelocidadeMediaPiloto() {
		return velocidadeMediaPiloto;
	}

	public void setVelocidadeMediaPiloto(Double velocidadeMediaPiloto) {
		this.velocidadeMediaPiloto = velocidadeMediaPiloto;
	}

	public int getNumeroTotalVoltas() {
		return numeroTotalVoltas;
	}

	public void setNumeroTotalVoltas(int numeroTotalVoltas) {
		this.numeroTotalVoltas = numeroTotalVoltas;
	}

	@Override
	public int compareTo(Piloto outroPiloto) {
		if (this.getVoltas().size() > outroPiloto.getVoltas().size()) {
			return -1;
		} else if (this.getVoltas().size() == outroPiloto.getVoltas().size()) {
			if (this.getTempoTotalProva().getTimeInMillis() > outroPiloto.getTempoTotalProva().getTimeInMillis()) {
				return 1;
			}
			if (this.getTempoTotalProva().getTimeInMillis() < outroPiloto.getTempoTotalProva().getTimeInMillis()) {
				return -1;
			}
		} else if (this.getVoltas().size() < outroPiloto.getVoltas().size()) {
			return 1;
		}

		return 0;
	}

	public String tempoTotalProvaFormatado() {
		DateFormat dateFormat = new SimpleDateFormat("m:ss.SSS");
		String strDate = dateFormat.format(getTempoTotalProva().getTime());
		return strDate;
	}

	public String getMelhorVolta() {
		return melhorVolta;
	}

	public void setMelhorVolta(String melhorVolta) {
		this.melhorVolta = melhorVolta;
	}

	@Override
	public String toString() {
		return "codigoPiloto=" + codigoPiloto + ", Nome=" + Nome + ", velocidadeMediaPiloto=" + velocidadeMediaPiloto
				+ ", Qtde Voltas Completadas=" + numeroTotalVoltas + ", tempoTotalProva=" + tempoTotalProvaFormatado()
				+ ",melhorVolta=" + getMelhorVolta() + "]";
	}

}