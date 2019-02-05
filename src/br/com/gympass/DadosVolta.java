package br.com.gympass;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DadosVolta implements Comparable<DadosVolta> {
	private Calendar hora;

	private Calendar tempoVolta;

	private Double VelocidadeMedia;
	private int numeroVolta;

	public DadosVolta(Calendar hora, Calendar tempoVolta, Double velocidadeMedia, int numeroVolta) {
		super();
		this.hora = hora;
		this.tempoVolta = tempoVolta;
		this.VelocidadeMedia = velocidadeMedia;
		this.numeroVolta = numeroVolta;
	}

	public Calendar getHora() {

		return hora;

	}

	public void setHora(Calendar hora) {

		this.hora = hora;

	}

	public Calendar getTempoVolta() {

		return tempoVolta;

	}

	public Long getTempoVoltaLong() {

		return tempoVolta.getTimeInMillis();

	}

	public void setTempoVolta(Calendar tempoVolta) {

		this.tempoVolta = tempoVolta;

	}

	public Double getVelocidadeMedia() {

		return VelocidadeMedia;

	}

	public void setVelocidadeMedia(Double velocidadeMedia) {

		VelocidadeMedia = velocidadeMedia;

	}

	public int getNumeroVolta() {
		return numeroVolta;
	}

	public void setNumeroVolta(int numeroVolta) {
		this.numeroVolta = numeroVolta;
	}

	public String tempoVoltaFormatado() {

		DateFormat dateFormat = new SimpleDateFormat("m:ss.SSS");
		String strDate = dateFormat.format(getTempoVolta().getTime());
		return strDate;
	}

	public String horaVoltaFormatado() {

		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		String strDate = dateFormat.format(getHora().getTime());
		return strDate;
	}

	public void somaTempoVolta(Calendar addend) {

		long sum = this.tempoVolta.getTimeInMillis() + addend.getTimeInMillis();
		Calendar sumCalendar = (Calendar) this.tempoVolta.clone();
		sumCalendar.setTimeInMillis(sum);
		tempoVolta = sumCalendar;
	}

	@Override
	public String toString() {
		return "DadosVolta [tempoVolta=" + tempoVoltaFormatado() + ", VelocidadeMedia=" + VelocidadeMedia
				+ ", numeroVolta=" + numeroVolta + "]";
	}

	@Override
	public int compareTo(DadosVolta dadosVolta) {
		if (this.getTempoVoltaLong() > dadosVolta.getTempoVoltaLong()) {
			return 1;
		}
		if (this.getTempoVoltaLong() < dadosVolta.getTempoVoltaLong()) {
			return -1;
		}
		return 0;
	}

}
