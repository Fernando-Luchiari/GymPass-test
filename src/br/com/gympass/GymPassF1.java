package br.com.gympass;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GymPassF1 {
	private List<Piloto> listaPiloto = new ArrayList<Piloto>();
	private Map<Integer, Piloto> hashPilotos = new HashMap<Integer, Piloto>();

	public void lerArquivo() {

		Path path = Paths.get("./gympasslog.log");

		try {

			List<String> linhasArquivo = Files.readAllLines(path);
			for (String linha : linhasArquivo) {
				carregarDados(linha);
			}

			exibirDadosCorrida();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public void exibirDadosCorrida() {
		hashPilotos.forEach((id, piloto) -> {
			Long tempoTotalCorrida = piloto.getVoltas().stream().mapToLong(DadosVolta::getTempoVoltaLong).sum();
			Double velocidadeTotalCorrida = piloto.getVoltas().stream().mapToDouble(DadosVolta::getVelocidadeMedia)
					.sum();

			Calendar sumCalendar = Calendar.getInstance();
			sumCalendar.setTimeInMillis(tempoTotalCorrida);
			piloto.setTempoTotalProva(sumCalendar);
			Double velocidadeMedia = formataVelocidade(velocidadeTotalCorrida / piloto.getVoltas().size());

			piloto.setVelocidadeMediaPiloto(velocidadeMedia);
			piloto.setNumeroTotalVoltas(piloto.getVoltas().size());

			Collections.sort(piloto.getVoltas());
			piloto.setMelhorVolta(piloto.getVoltas().get(0).getNumeroVolta() + "ºvolta-"
					+ piloto.getVoltas().get(0).tempoVoltaFormatado());

			listaPiloto.add(piloto);
		});
		Collections.sort(listaPiloto);
		int i = 0;
		for (Piloto dPiloto : listaPiloto) {
			System.out.println("Posição" + (i + 1) + ": " + dPiloto);
			i++;
		}

	}

	public void carregarDados(String linha) throws ParseException {

		String regexHoraChegada = "(\\d+:\\d+:\\d+.\\d+)";
		String regexTempoVolta = "(\\s\\d+:\\d+.\\d+)";
		String regexNumVolta = "(\\s[0-9]{1}\\s)";
		String regexVelocidadeMedia = "([0-9]{2},[0-9]{3})";
		String regexPiloto = "(\\s[A-Za-z.]+\\s)";
		String regexcodigoPiloto = "(\\s[0-9]{3}\\s)";

		String nomePiloto = null;
		int codigoPiloto = 0;
		Calendar tempoVolta = null;
		double velocidadeMedia = 0;
		Calendar hora = null;
		int numeroVolta = 0;

		Pattern pNomePiloto = Pattern.compile(regexPiloto);
		Matcher mtch = pNomePiloto.matcher(linha);
		if (mtch.find()) {
			nomePiloto = mtch.group(0).trim();

		}

		Pattern pCodigoPiloto = Pattern.compile(regexcodigoPiloto);
		Matcher mtchCP = pCodigoPiloto.matcher(linha);
		if (mtchCP.find()) {
			codigoPiloto = Integer.parseInt(mtchCP.group(0).trim());
		}

		Pattern pTempoVolta = Pattern.compile(regexTempoVolta);
		Matcher mtchTV = pTempoVolta.matcher(linha);
		if (mtchTV.find()) {
			SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
			Date date = sdf.parse(mtchTV.group(0).trim());
			tempoVolta = Calendar.getInstance();
			tempoVolta.setTime(date);

		}
		Pattern pVelocidadeMedia = Pattern.compile(regexVelocidadeMedia);
		Matcher mtchVM = pVelocidadeMedia.matcher(linha);
		if (mtchVM.find()) {
			velocidadeMedia = formataVelocidade(Double.parseDouble(mtchVM.group(0).replace(",", ".")));
		}

		Pattern pHoraChegada = Pattern.compile(regexHoraChegada);
		Matcher mtchHC = pHoraChegada.matcher(linha);
		if (mtchHC.find()) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
			Date date = sdf.parse(mtchHC.group(0).trim());
			hora = Calendar.getInstance();
			hora.setTime(date);

		}

		Pattern pNumVolta = Pattern.compile(regexNumVolta);
		Matcher mtchNumVolta = pNumVolta.matcher(linha);
		if (mtchNumVolta.find()) {

			numeroVolta = Integer.parseInt(mtchNumVolta.group(0).trim());
		}

		DadosVolta dadosVolta = new DadosVolta(hora, tempoVolta, velocidadeMedia, numeroVolta);
		Piloto piloto = new Piloto(codigoPiloto, nomePiloto);

		if (!hashPilotos.containsKey(codigoPiloto)) {
			hashPilotos.put(codigoPiloto, piloto);
			hashPilotos.get(codigoPiloto).getVoltas().add(dadosVolta);
		} else {
			hashPilotos.get(codigoPiloto).getVoltas().add(dadosVolta);
		}

	}

	public Double formataVelocidade(Double velocidade) {
		DecimalFormat formato = new DecimalFormat("##.###");
		Double numero = Double.parseDouble(formato.format(velocidade).replaceAll(",", "."));
		return numero;
	}

	public static void main(String[] args) {
		GymPassF1 rg = new GymPassF1();
		rg.lerArquivo();
	}
}